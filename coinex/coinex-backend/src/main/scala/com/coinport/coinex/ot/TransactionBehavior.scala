package com.coinport.coinex.ot

import akka.actor.{ Actor, ActorLogging }
import com.coinport.coinex.data._
import com.mongodb.casbah.Imports._
import akka.event.LoggingReceive
import com.coinport.coinex.common.ExtendedView
import akka.persistence.Persistent

class TransactionReader(db: MongoDB) extends ExtendedView with TransactionMongoHandler with ActorLogging {
  override val processorId = "coinex_mup"
  val coll = db("transaction")

  def receive = LoggingReceive {
    case DebugDump =>
      log.info("TransactionReader")

    case q: QueryTransaction =>
      sender ! QueryTransactionResult(getItems(q), countItems(q))
  }
}

class TransactionWriter(db: MongoDB) extends ExtendedView with TransactionMongoHandler with ActorLogging {
  override val processorId = "coinex_mup"
  val coll = db("transaction")

  def receive = LoggingReceive {
    case DebugDump =>
      log.info("TransactionWriter")

    case e @ Persistent(OrderSubmitted(orderInfo, txs), _) =>
      txs foreach { t =>
        val amount = Math.abs(t.takerUpdate.current.quantity - t.takerUpdate.previous.quantity)
        val reverseAmount = Math.abs(t.makerUpdate.previous.quantity - t.makerUpdate.current.quantity)

        val price = reverseAmount.toDouble / amount.toDouble

        val (taker, toId) = (t.takerUpdate.current.userId, t.takerUpdate.current.id)
        val (maker, moId) = (t.makerUpdate.current.userId, t.makerUpdate.current.id)

        val item = TransactionItem(tid = t.id, price = price, volume = amount, amount = reverseAmount, taker = taker,
          maker = maker, tOrder = toId, mOrder = moId, side = t.side, timestamp = t.timestamp)
        addItem(item)
      }
  }
}
