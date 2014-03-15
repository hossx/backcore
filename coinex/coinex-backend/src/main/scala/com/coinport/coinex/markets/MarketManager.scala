/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 *
 * MarketManager is the maintainer of a Market. It executes new orders before
 * they are added into a market as pending orders. As execution results, a list
 * of Transactions are generated and returned.
 *
 * MarketManager can be used by an Akka persistent processor or a view
 * to reflect pending orders and market depth.
 *
 * Note this class does NOT depend on event-sourcing framework we choose. Please
 * keep it plain old scala/java.
 */

package com.coinport.coinex.markets

import com.coinport.coinex.data._
import com.coinport.coinex.common.StateManager
import scala.collection.mutable.ListBuffer
import Implicits._
import OrderStatus._

class MarketManager(headSide: MarketSide)(implicit val now: () => Long) extends StateManager[MarketState] {
  initWithDefaultState(MarketState(headSide))
  private var collectTxs = true

  def addOrder(sellSide: MarketSide, order: Order): OrderSubmitted = {
    val orderWithTime = order.copy(timestamp = Some(now()))
    val buySide = sellSide.reverse

    def sellMpos = state.marketPriceOrderPool(sellSide)
    def buyMpos = state.marketPriceOrderPool(buySide)
    def buyLpos = state.limitPriceOrderPool(buySide)

    var sellOrder = orderWithTime
    var totalOutAmount = 0L
    var totalInAmount = 0L
    var continue = sellMpos.isEmpty
    val txBuffer = new ListBuffer[Transaction]

    def foundMatching(buyOrder: Order, price: Double) {
      val outAmount = Math.min(sellOrder.maxOutAmount(price), buyOrder.maxInAmount(1 / price))
      val inAmount = (outAmount * price).toLong
      val updatedSellOrder = sellOrder.copy(quantity = sellOrder.quantity - outAmount, takeLimit = sellOrder.takeLimit.map(_ - inAmount))
      val updatedBuyOrder = buyOrder.copy(quantity = buyOrder.quantity - inAmount, takeLimit = buyOrder.takeLimit.map(_ - outAmount))

      txBuffer += Transaction(now(), sellOrder --> updatedSellOrder, buyOrder --> updatedBuyOrder)

      if (updatedSellOrder.isFullyExecuted) {
        continue = false
      }

      state = state.removeOrder(buySide, buyOrder.id)
      if (!updatedBuyOrder.isFullyExecuted) {
        state = state.addOrder(buySide, updatedBuyOrder)
        continue = false
      }
      totalOutAmount += outAmount
      totalInAmount += inAmount
      sellOrder = updatedSellOrder
    }

    while (continue) {
      buyMpos.headOption match {
        // new LPO to match existing MPOs
        case Some(buyOrder) if sellOrder.vprice > 0 => foundMatching(buyOrder, sellOrder.vprice)
        case _ =>
          buyLpos.headOption match {
            // new LPO or MPO to match existing LPOs
            case Some(buyOrder) if buyOrder.vprice * sellOrder.vprice <= 1 => foundMatching(buyOrder, 1 / buyOrder.vprice)
            case _ => continue = false
          }
      }
    }

    if (!sellOrder.isFullyExecuted) {
      state = state.addOrder(sellSide, sellOrder)
    }

    val status =
      if (sellOrder.isFullyExecuted) OrderStatus.FullyExecuted
      else if (totalOutAmount > 0) OrderStatus.PartiallyExecuted
      else OrderStatus.Pending

    val txs = txBuffer.toSeq
    val orderInfo = OrderInfo(sellSide, orderWithTime, totalOutAmount, totalInAmount, status, txs.lastOption.map(_.timestamp))

    OrderSubmitted(orderInfo, txs)
  }

  def removeOrder(side: MarketSide, id: Long): Option[Order] = {
    val order = state.getOrder(side, id)
    order foreach { _ => state = state.removeOrder(side, id) }
    order
  }
}