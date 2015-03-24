package com.coinport.coinex.ordertx

import akka.actor.{ Actor, ActorLogging }
import com.coinport.coinex.data._
import com.mongodb.casbah.Imports._
import akka.event.LoggingReceive
import com.coinport.coinex.common.{ SimpleManager, ExtendedView, ExtendedActor }
import com.coinport.coinex.common.PersistentId._
import Implicits._
import akka.persistence.Persistent

class TransactionReader(val db: MongoDB, val index: Int) extends ExtendedActor with TransactionBehavior with ActorLogging {
  val coll = db("transactions")

  def receive = LoggingReceive {
    case q: QueryTransaction =>
      println(s">>>>> use transaction reader actor index : ${index}")
      val count = if (q.needCount) countItems(q) else -1
      sender ! QueryTransactionResult(getItems(q), count)
  }
}

class TransactionWriter(val db: MongoDB) extends ExtendedView with TransactionBehavior with ActorLogging {
  override val processorId = MARKET_UPDATE_PROCESSOR <<
  override val viewId = TRANSACTION_WRITE_VIEW <<

  val manager = new SimpleManager()

  val coll = db("transactions")

  def receive = LoggingReceive {
    case e @ Persistent(OrderSubmitted(orderInfo, txs), _) =>
      txs foreach (addItem)
  }
}
