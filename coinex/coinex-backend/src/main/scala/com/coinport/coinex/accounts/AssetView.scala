/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 */

package com.coinport.coinex.accounts

import com.coinport.coinex.common.Constants._
import com.coinport.coinex.common.ExtendedView
import akka.event.LoggingReceive
import com.coinport.coinex.data._
import TransferType._
import com.coinport.coinex.data.Implicits._
import com.coinport.coinex.common.PersistentId._
import akka.persistence.Persistent
import Implicits._

class AssetView extends ExtendedView {
  override val processorId = ACCOUNT_PROCESSOR <<
  override val viewId = USER_ASSET <<
  val manager = new AssetManager()

  def receive = LoggingReceive {
    case Persistent(cts: CryptoTransferSucceeded, _) =>
      cts.transfers foreach (succeededTransfer(_))

    case Persistent(acts: AdminConfirmTransferSuccess, _) =>
      succeededTransfer(acts.transfer)

    case Persistent(processed: AdminConfirmTransferProcessed, _) =>
      succeededTransfer(processed.transfer)

    case Persistent(result: CryptoTransferResult, _) =>
      result.multiTransfers.values foreach {
        tmf =>
          tmf.transfers.filter(_.status == TransferStatus.Succeeded).foreach(succeededTransfer(_))
      }

    case e @ Persistent(OrderSubmitted(originOrderInfo, txs), _) =>
      if (!txs.isEmpty) {
        val side = originOrderInfo.side

        val toutCurrency = side.outCurrency
        val tinCurrency = side.inCurrency

        val taker = originOrderInfo.order.userId
        val timestamp = originOrderInfo.lastTxTimestamp.get

        manager.updateAsset(taker, timestamp, tinCurrency, originOrderInfo.inAmount)
        manager.updateAsset(taker, timestamp, toutCurrency, -originOrderInfo.outAmount)

        txs foreach {
          tx =>
            val min = tx.takerUpdate.previous.quantity - tx.takerUpdate.current.quantity
            val mout = tx.makerUpdate.previous.quantity - tx.makerUpdate.current.quantity

            val maker = tx.makerUpdate.current.userId
            val timestamp2 = tx.timestamp

            manager.updateAsset(maker, timestamp2, toutCurrency, min)
            manager.updateAsset(maker, timestamp2, tinCurrency, -mout)
        }

        manager.updatePrice(side, timestamp, txs.last.makerUpdate.current.price.get.reciprocal.value)
        manager.updatePrice(side.reverse, timestamp, txs.last.makerUpdate.current.price.get.value)
      }

    case Persistent(m: DoRequestPayment, _) =>
      if (m.payment.payer != NULL_USER_ID)
        manager.updateAsset(m.payment.payer, m.payment.created.getOrElse(0), m.payment.currency, -m.payment.amount)
      if (m.payment.payee != NULL_USER_ID)
        manager.updateAsset(m.payment.payee, m.payment.created.getOrElse(0), m.payment.currency, m.payment.amount)

    case q: QueryAsset =>
      val start = Math.min(q.from, q.to)
      val stop = Math.max(q.from, q.to)

      val historyAsset = HistoryAsset(manager.getHistoryAsset(q.uid, start, stop))
      val currentAsset = CurrentAsset(manager.getCurrentAsset(q.uid))
      val historyPrice = HistoryPrice(manager.getHistoryPrice(start, stop).map(x => x._1 -> x._2))
      val currentPrice = CurrentPrice(manager.getCurrentPrice)

      sender ! QueryAssetResult(currentAsset, historyAsset, currentPrice, historyPrice)
  }

  private def succeededTransfer(t: AccountTransfer) {
    t.`type` match {
      case Deposit =>
        // Deposit fee not included the t.amount
        val feeAmount = t.fee match {
          case Some(f) if f.amount > 0 => f.amount
          case _ => 0L
        }
        manager.updateAsset(t.userId, t.updated.get, t.currency, t.amount - feeAmount)
      // Withdrawal fee is included in t.amount, so no need to substract again
      case Withdrawal => manager.updateAsset(t.userId, t.updated.get, t.currency, -t.amount)
      case _ =>
    }
  }
}
