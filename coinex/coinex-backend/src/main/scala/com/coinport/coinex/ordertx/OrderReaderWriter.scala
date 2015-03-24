package com.coinport.coinex.ordertx

import com.coinport.coinex.data._
import com.mongodb.casbah.MongoDB
import akka.actor.{ ActorLogging, Actor }
import akka.event.LoggingReceive
import Implicits._
import com.coinport.coinex.common.{ SimpleManager, ExtendedView, ExtendedActor }
import com.coinport.coinex.common.PersistentId._
import akka.persistence.Persistent

class OrderReader(db: MongoDB, index: Int) extends ExtendedActor with OrderMongoHandler with ActorLogging {
  val coll = db("orders")

  def receive = LoggingReceive {
    case q: QueryOrder =>
      println(s">>>>> use order reader actor index : ${index}")
      val count = if (q.needCount) countItems(q) else -1
      sender ! QueryOrderResult(getItems(q), count)
  }
}

class OrderWriter(db: MongoDB) extends ExtendedView with OrderMongoHandler with ActorLogging {
  override val processorId = MARKET_UPDATE_PROCESSOR <<
  override val viewId = ORDER_WRITE_VIEW <<

  val manager = new SimpleManager()

  val coll = db("orders")

  def receive = LoggingReceive {
    case Persistent(OrderCancelled(side, order), _) => cancelItem(order.id)

    case e @ Persistent(OrderSubmitted(orderInfo, txs), _) =>
      txs.foreach {
        tx =>
          val quantity = tx.makerUpdate.current.quantity
          val inAmount = tx.makerUpdate.current.inAmount
          val status =
            if (!tx.makerUpdate.current.canBecomeMaker) OrderStatus.FullyExecuted
            else OrderStatus.PartiallyExecuted

          updateItem(tx.makerUpdate.current.id, inAmount, quantity, status.getValue(), orderInfo.side.reverse,
            tx.timestamp, tx.makerUpdate.current.refund)
      }

      addItem(orderInfo, if (txs.isEmpty) orderInfo.order.quantity else txs.last.takerUpdate.current.quantity)
  }
}
