package com.coinport.coinex.common

import com.coinport.coinex.data.RedeliverFilterData
import scala.collection.mutable.SortedSet

class RedeliverFilter(data: RedeliverFilterData) {
  private[common] var processedIds = SortedSet[Long](data.processedIds: _*).takeRight(data.maxSize)

  def filter(id: Long)(op: Long => Unit) = if (!hasProcessed(id)) { op(id) }

  def getThrift = RedeliverFilterData(processedIds.toSeq, data.maxSize)

  def hasProcessed(id: Long) = {
    val processed = (id < processedIds.headOption.getOrElse(0L) || processedIds.contains(id))
    if (!processed) {
      processedIds += id
      if (processedIds.size > data.maxSize) processedIds = processedIds.takeRight(data.maxSize)
    }
    processed
  }
}
