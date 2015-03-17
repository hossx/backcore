package com.coinport.coinex.ordertx

import com.mongodb.casbah.Imports._
import com.coinport.coinex.data._
import Implicits._
import com.coinport.coinex.common.mongo.SimpleJsonMongoCollection
import com.coinport.coinex.serializers.ThriftBinarySerializer

trait TransactionBehavior {
  val TID = "_id"
  val TAKER_ID = "tid"
  val MAKER_ID = "mid"
  val TAKER_ORDER_ID = "toid"
  val MAKER_ORDER_ID = "moid"
  val TIMESTAMP = "@"
  val MARKET = "m"
  val SIDE = "s"
  val TRANSACTION = "t"

  val coll: MongoCollection
  val converter = new ThriftBinarySerializer

  def addItem(item: Transaction) = coll.save(toBson(item), com.mongodb.WriteConcern.ACKNOWLEDGED)

  def countItems(q: QueryTransaction) =
    try {
      coll.count(mkQuery(q))
    } catch {
      case e: Exception =>
        0L
    }

  def getItems(q: QueryTransaction): Seq[Transaction] = {
    try {
      if (q.oid.isDefined) {
        val basicQuery = mkQueryWithoutOr(q)
        var queryPart1 = basicQuery ++ MongoDBObject(TAKER_ORDER_ID -> q.oid.get)
        var queryPart2 = basicQuery ++ MongoDBObject(MAKER_ORDER_ID -> q.oid.get)

        if (!q.fromTid.isDefined && q.cursor.skip > 0) {
          val part1SkipSeq = coll.find(queryPart1, MongoDBObject(TID -> 1)).sort(DBObject(TID -> -1)).limit(q.cursor.skip).map(_.getAsOrElse[Long](TID, 0L)).toSeq
          val part2SkipSeq = coll.find(queryPart2, MongoDBObject(TID -> 1)).sort(DBObject(TID -> -1)).limit(q.cursor.skip).map(_.getAsOrElse[Long](TID, 0L)).toSeq
          val newCursor = findCursor(part1SkipSeq, part2SkipSeq)
          queryPart1 = queryPart1 ++ (TID $lt newCursor)
          queryPart2 = queryPart2 ++ (TID $lt newCursor)
        }

        val queryPart1Result = coll.find(queryPart1).sort(DBObject(TID -> -1)).limit(q.cursor.limit).map(toClass(_)).toSeq
        val queryPart2Result = coll.find(queryPart2).sort(DBObject(TID -> -1)).limit(q.cursor.limit).map(toClass(_)).toSeq
        (queryPart1Result ++ queryPart2Result).distinct.sortWith((f, t) => f.id > t.id).take(q.cursor.limit)
      } else if (q.uid.isDefined) {
        val basicQuery = mkQueryWithoutOr(q)
        var queryPart1 = basicQuery ++ MongoDBObject(MAKER_ID -> q.uid.get)
        var queryPart2 = basicQuery ++ MongoDBObject(TAKER_ID -> q.uid.get)
        if (!q.fromTid.isDefined && q.cursor.skip > 0) {
          val part1SkipSeq = coll.find(queryPart1, MongoDBObject(TID -> 1)).sort(DBObject(TID -> -1)).limit(q.cursor.skip).map(_.getAsOrElse[Long](TID, 0L)).toSeq
          val part2SkipSeq = coll.find(queryPart2, MongoDBObject(TID -> 1)).sort(DBObject(TID -> -1)).limit(q.cursor.skip).map(_.getAsOrElse[Long](TID, 0L)).toSeq
          val newCursor = findCursor(part1SkipSeq, part2SkipSeq)
          queryPart1 = queryPart1 ++ (TID $lt newCursor)
          queryPart2 = queryPart2 ++ (TID $lt newCursor)
        }
        val queryPart1Result = coll.find(queryPart1).sort(DBObject(TID -> -1)).limit(q.cursor.limit).map(toClass(_)).toSeq
        val queryPart2Result = coll.find(queryPart2).sort(DBObject(TID -> -1)).limit(q.cursor.limit).map(toClass(_)).toSeq
        (queryPart1Result ++ queryPart2Result).distinct.sortWith((f, t) => f.id > t.id).take(q.cursor.limit)
      } else {
        coll.find(mkQueryWithoutOr(q)).sort(DBObject(TID -> -1)).skip(q.cursor.skip).limit(q.cursor.limit).map(toClass(_)).toSeq
      }
    } catch {
      case e: Exception =>
        Seq.empty[Transaction]
    }
  }

  private def toBson(t: Transaction) = {
    val market = Market(t.side._1, t.side._2)
    val side = t.side.ordered
    MongoDBObject(
      TID -> t.id, TAKER_ID -> t.takerUpdate.current.userId, TAKER_ORDER_ID -> t.takerUpdate.current.id,
      MAKER_ID -> t.makerUpdate.current.userId, MAKER_ORDER_ID -> t.makerUpdate.current.id,
      MARKET -> market.toString, SIDE -> side, TIMESTAMP -> t.timestamp, TIMESTAMP -> t.timestamp,
      TRANSACTION -> converter.toBinary(t))
  }

  private def toClass(obj: MongoDBObject) =
    converter.fromBinary(obj.getAs[Array[Byte]](TRANSACTION).get, Some(classOf[Transaction.Immutable])).asInstanceOf[Transaction]

  private def mkQuery(q: QueryTransaction): MongoDBObject = {
    var query = mkQueryWithoutOr(q)
    if (q.oid.isDefined) query ++= $or(TAKER_ORDER_ID -> q.oid.get, MAKER_ORDER_ID -> q.oid.get)
    if (q.uid.isDefined) query ++= $or(TAKER_ID -> q.uid.get, MAKER_ID -> q.uid.get)
    query
  }

  private def mkQueryWithoutOr(q: QueryTransaction): MongoDBObject = {
    var query = MongoDBObject()
    if (q.tid.isDefined) query ++= MongoDBObject(TID -> q.tid.get)
    if (q.side.isDefined) query ++= {
      val qs = q.side.get
      val market = Market(qs.side.inCurrency, qs.side.outCurrency).toString
      val side = qs.side.ordered
      if (qs.bothSide) MongoDBObject(MARKET -> market)
      else MongoDBObject(MARKET -> q.side.get.side.S, SIDE -> side)
    }
    if (!q.tid.isDefined && q.fromTid.isDefined) query ++= (TID $lt q.fromTid.get)
    query
  }

  private def findCursor(p1: Seq[Long], p2: Seq[Long]) = (p1 ++ p2).distinct.sortWith((f, t) => f > t)((p1.size + p2.size) / 2 - 1)
}
