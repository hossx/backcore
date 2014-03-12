/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 */

package com.coinport.coinex.markets

import akka.persistence.Persistent
import com.coinport.coinex.data._
import com.coinport.coinex.common.ExtendedView
import Implicits._

class MarketView(marketSide: MarketSide) extends ExtendedView {
  override def processorId = "coinex_mp_" + marketSide.asString
  val manager = new MarketManager(marketSide)
  var lastPrice: Option[Price] = None

  def receive = {
    case DebugDump =>
      log.info("state: {}", manager())
      
    case x =>
      log.info("~~~ saw: " + x)
      if (receiveMessage.isDefinedAt(x)) receiveMessage(x)
  }

  def receiveMessage: Receive = {
    case Persistent(DoCancelOrder(side, orderId), _) =>
      manager.removeOrder(side, orderId)

    case Persistent(OrderSubmitted(side, order: Order), _) =>
      val txs = manager.addOrder(side, order)
      txs.headOption foreach {
        tx =>
          lastPrice = Some(Price(tx.taker.currency ~> tx.maker.currency, tx.maker.quantity.toDouble / tx.taker.quantity))
      }

    case QueryMarket(side, depth) =>
      val price = lastPrice map { p => if (p.side == side) p else p.reverse}
      sender ! QueryMarketResult(price,
        manager().limitPriceOrderPool(side).take(depth).toSeq,
        manager().limitPriceOrderPool(side.reverse).take(depth).toSeq)
  }
}