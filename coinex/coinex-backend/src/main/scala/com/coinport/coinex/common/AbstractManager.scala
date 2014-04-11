package com.coinport.coinex.common

import com.coinport.coinex.data._
import scala.collection.mutable.Map

abstract class AbstractManager[T <: AnyRef] {
  protected val filters = Map.empty[String, RedeliverFilter]

  def getSnapshot: T
  def loadSnapshot(s: T): Unit

  def hasProcessed(channel: String, id: Long) = {
    val filter = filters.getOrElseUpdate(channel, new RedeliverFilter(RedeliverFilterData(Seq.empty[Long], 100)))
    filter.hasProcessed(id)
  }

  def getFiltersSnapshot = RedeliverFilters(filters.map { kv => kv._1 -> kv._2.getThrift })

  def loadFiltersSnapshot(snapshot: RedeliverFilters) = {
    filters.clear
    filters ++= snapshot.filterMap.map { kv => kv._1 -> new RedeliverFilter(kv._2) }
  }
}
