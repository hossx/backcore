package com.coinport.coinex.opendata

import scala.concurrent.duration._
import com.coinport.coinex.serializers.BaseJsonSerializer

class OpenDataConfig() {
  val enableExportData: Boolean = false
  val pFileMap = collection.mutable.Map.empty[String, String]
  val snapshotHdfsDir: String = "/snapshot"
  val exportSnapshotHdfsDir: String = "/export/snapshot"
  val exportMessagesHdfsDir: String = "/export/messages"
  val debugSnapshotHdfsDir: String = "/debug/snapshot"
  val hdfsHost: String = "hdfs://hadoop:54310"
  val scheduleInterval = 60 seconds // check if there are data to export every 1 minute.
  val snapshotSerializerMap: Map[Class[_], BaseJsonSerializer] = Map.empty[Class[_], BaseJsonSerializer]
}
