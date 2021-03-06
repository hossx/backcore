package com.coinport.coinex.transfer

import akka.event.LoggingAdapter
import com.coinport.coinex.common.mongo.SimpleJsonMongoCollection
import com.coinport.coinex.data._
import com.coinport.coinex.data.TransferStatus._
import com.coinport.coinex.data.TransferType._
import com.coinport.coinex.common.Constants._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

trait CryptoCurrencyTransferBase {
  val sigId2MinerFeeMap: Map[String, Long] = Map.empty[String, Long]
  val id2HandlerMap: Map[Long, CryptoCurrencyTransferHandler] = Map.empty[Long, CryptoCurrencyTransferHandler]
  val succeededId2HandlerMap: Map[Long, CryptoCurrencyTransferHandler] = Map.empty[Long, CryptoCurrencyTransferHandler]
  val msgBoxMap: Map[Long, CryptoCurrencyTransferItem] = Map.empty[Long, CryptoCurrencyTransferItem]

  var manager: AccountTransferManager = null
  var transferHandler: SimpleJsonMongoCollection[AccountTransfer, AccountTransfer.Immutable] = null
  var transferItemHandler: SimpleJsonMongoCollection[CryptoCurrencyTransferItem, CryptoCurrencyTransferItem.Immutable] = null
  var logger: LoggingAdapter = null
  var succeededRetainNum = collection.immutable.Map.empty[Currency, Int]

  def getNoneSigId = "NONE_SIGID_" + System.currentTimeMillis().toString

  implicit var env: TransferEnv = null

  def setEnv(env: TransferEnv): CryptoCurrencyTransferBase = {
    this.env = env
    manager = env.manager
    transferHandler = env.transferHandler
    transferItemHandler = env.transferItemHandler
    logger = env.logger
    succeededRetainNum = env.succeededRetainNum
    this
  }

  def init(): CryptoCurrencyTransferBase = {
    msgBoxMap.clear()
    this
  }

  def handleTx(currency: Currency, tx: CryptoCurrencyTransaction, timestamp: Option[Long]) {
    refreshLastBlockHeight(currency, tx)
    innerHandleTx(currency, tx, timestamp)
  }

  def hasUnExpiredItems(expireStartTime: Long): Boolean = {
    id2HandlerMap.values.exists(i => i.item.created.get > expireStartTime)
  }

  def handleBackcoreFail(info: CryptoCurrencyTransferInfo, currency: Currency, timestamp: Option[Long], error: Option[ErrorCode]) {
    if (id2HandlerMap.contains(info.id)) {
      handleFailed(id2HandlerMap(info.id).setTimeStamp(timestamp), error)
    } else {
      logger.warning(s"""${"~" * 50} ${currency.toString} backcore Fail not match existing item : id2HandleMap.size = ${id2HandlerMap.size}, info = ${info.toString}""")
    }
  }

  protected def innerHandleTx(currency: Currency, tx: CryptoCurrencyTransaction, timestamp: Option[Long]) {}

  def checkConfirm(currency: Currency, timestamp: Option[Long], confirmNum: Option[Int], enableUsersToInner: Option[Boolean]) {
    val lastBlockHeight: Long = manager.getLastBlockHeight(currency)
    id2HandlerMap.values filter (i => i.item.currency == currency && i.item.status.get != TransferStatus.Succeeded && i.item.status.get != TransferStatus.ReorgingSucceeded) foreach {
      handler =>
        handler.setTimeStamp(timestamp).setConfirmNum(confirmNum).setEnableUsersToInner(enableUsersToInner)
        if (handler.checkConfirm(lastBlockHeight) && handler.item.status.get == Succeeded) {
          handleSucceeded(handler.item.id)
        }
    }
    succeededId2HandlerMap.values filter (_.item.currency == currency) foreach {
      handler =>
        if (handler.checkRemoveSucceeded(lastBlockHeight)) {
          succeededId2HandlerMap.remove(handler.item.id)
        }
    }
  }

  def loadSnapshotItems(snapshotItems: Option[collection.Map[Long, CryptoCurrencyTransferItem]]) {
    id2HandlerMap.clear()
    if (snapshotItems.isDefined)
      snapshotItems.get.values map {
        item => id2HandlerMap.put(item.id, newHandlerFromItem(item))
      }
  }

  def loadSnapshotSucceededItems(snapshotItems: Option[collection.Map[Long, CryptoCurrencyTransferItem]]) {
    succeededId2HandlerMap.clear()
    if (snapshotItems.isDefined)
      snapshotItems.get.values map {
        item => succeededId2HandlerMap.put(item.id, newHandlerFromItem(item))
      }
  }

  def loadSigId2MinerFeeMap(snapshotMap: Option[collection.Map[String, Long]]) {
    sigId2MinerFeeMap.clear()
    if (snapshotMap.isDefined)
      sigId2MinerFeeMap ++= snapshotMap.get
  }

  def getTransferItemsMap(): Map[Long, CryptoCurrencyTransferItem] = {
    id2HandlerMap map {
      kv => kv._1 -> kv._2.item.copy()
    }
  }

  def getSucceededItemsMap(): Map[Long, CryptoCurrencyTransferItem] = {
    succeededId2HandlerMap map {
      kv => kv._1 -> kv._2.item.copy()
    }
  }

  def getSigId2MinerFeeMap(): Map[String, Long] = {
    sigId2MinerFeeMap.clone()
  }

  def reOrganize(currency: Currency, reOrgBlock: BlockIndex, manager: AccountTransferManager, timestamp: Option[Long]) {
    reOrgBlock.height match {
      case Some(reOrgHeight) if reOrgHeight < manager.getLastBlockHeight(currency) =>
        id2HandlerMap.values.filter(_.item.currency == currency) foreach {
          _.setTimeStamp(timestamp).reOrgnize(reOrgHeight)
        }
        succeededId2HandlerMap.values filter (_.item.currency == currency) foreach {
          handler =>
            if (handler.setTimeStamp(timestamp).reOrgnizeSucceeded(reOrgHeight)) {
              succeededId2HandlerMap.remove(handler.item.id)
            }
        }
      case _ =>
        logger.warning(s"""${"~" * 50} ${currency.toString} reOrgnize() wrong reOrgnize height : [${manager.getLastBlockHeight(currency)}, ${reOrgBlock.toString()}]""")
    }
  }

  def getMsgToBitway(currency: Currency): List[CryptoCurrencyTransferInfo] = {
    val resList = ListBuffer.empty[CryptoCurrencyTransferInfo]
    msgBoxMap.values map {
      item =>
        item2BitwayInfo(item) match {
          case Some(info) if item.currency == currency =>
            resList += info
          case _ =>
        }
    }
    resList.toList
  }

  // currency, (sigId, (list[AccountTransfer], minerFee))
  def getMsgToAccount(currency: Currency): Map[String, (ListBuffer[AccountTransfer], Option[Long])] = {
    val resMap = Map.empty[String, (ListBuffer[AccountTransfer], Option[Long])]
    val noneSigId = getNoneSigId
    msgBoxMap.values map {
      item =>
        item2AccountTransfer(item) match {
          case Some(info) if item.currency == currency =>
            val sigIdForKey = item.sigId match {
              //Failed item will not contain sigId
              case Some(sigId) => sigId
              case _ => noneSigId
            }
            if (!resMap.contains(sigIdForKey)) {
              val minerFee = item.status match {
                // only count Succeed transactions minerFee
                case Some(Succeeded) if item.sigId.isDefined => sigId2MinerFeeMap.remove(sigIdForKey)
                case Some(Succeeded) if !item.sigId.isDefined =>
                  logger.error(s"""${"~" * 50} getMsgToAccount succeeded item without sigId defined : ${item.toString}""")
                  None
                case _ => None
              }
              resMap.put(sigIdForKey, (ListBuffer.empty[AccountTransfer], minerFee))
            }
            resMap(sigIdForKey)._1.append(info)
          case _ =>
        }
    }
    resMap
  }

  def manualFailTransfer(transferId: Long, status: TransferStatus) {
    val toFailHandlers = id2HandlerMap.values.filter(_.item.accountTransferId == Some(transferId)).toSeq
    if (toFailHandlers.size == 1) {
      val handler = id2HandlerMap.remove(toFailHandlers.head.item.id)
      postProcessManualFail(handler.get, status)
    } else if (toFailHandlers.size > 1) {
      logger.error(s"Found more than one transferItem with same accountTransferId ${transferId}, items : ${toFailHandlers}")
    }
  }

  protected def item2BitwayInfo(item: CryptoCurrencyTransferItem): Option[CryptoCurrencyTransferInfo] = {
    item.status match {
      case Some(Confirming) =>
        item2CryptoCurrencyTransferInfo(item)
      case _ =>
        logger.warning(s"""${"~" * 50} item2BitwayInfo() get unexpected item : ${item.toString}""")
        None
    }
  }

  protected def postProcessManualFail(handler: CryptoCurrencyTransferHandler, status: TransferStatus) {}

  protected def item2CryptoCurrencyTransferInfo(item: CryptoCurrencyTransferItem): Option[CryptoCurrencyTransferInfo] = None

  protected def item2AccountTransfer(item: CryptoCurrencyTransferItem): Option[AccountTransfer] = {
    item.status match {
      case Some(Succeeded) => // batch Set miner fee before sent message to accountProcessor
        transferHandler.get(item.accountTransferId.get)
      case Some(Failed) if item.txType.get != UserToHot && item.txType.get != ColdToHot && item.txType.get != HotToCold => //UserToHot fail will do nothing
        transferHandler.get(item.accountTransferId.get)
      case _ =>
        logger.warning(s"""${"~" * 50} item2AccountTransfer() meet unexpected item : ${item.toString}""")
        None
    }
  }

  protected def newHandlerFromItem(item: CryptoCurrencyTransferItem): CryptoCurrencyTransferHandler

  protected def handleSucceeded(itemId: Long) {
    id2HandlerMap.remove(itemId) match {
      case Some(handler) =>
        handler.onSucceeded()
        succeededId2HandlerMap.put(itemId, handler)
        msgBoxMap.put(handler.item.id, handler.item)
      case _ =>
    }
  }

  protected def handleFailed(handler: CryptoCurrencyTransferHandler, error: Option[ErrorCode] = None) {}

  protected def updateSigId2MinerFee(tx: CryptoCurrencyTransaction) {
    tx.minerFee match {
      case Some(fee) if tx.sigId.isDefined =>
        if (tx.txType.isDefined && tx.txType.get != Deposit && tx.txType.get != DepositHot) { //Deposit should ignore minerFee
          tx.status match {
            case Failed => sigId2MinerFeeMap.remove(tx.sigId.get)
            case _ => sigId2MinerFeeMap.put(tx.sigId.get, fee)
          }
        }
      case Some(_) =>
        logger.error(s"""${"~" * 50} updateSigId2MinerFee minerFee defined without sigId defined : ${tx.toString}""")
      case None =>
    }
  }

  protected def refreshLastBlockHeight(currency: Currency, tx: CryptoCurrencyTransaction) {
    val txHeight: Long = if (tx.includedBlock.isDefined) tx.includedBlock.get.height.getOrElse(0L) else 0L
    if (manager.getLastBlockHeight(currency) < txHeight) manager.setLastBlockHeight(currency, txHeight)
  }

  def newHandlerFromAccountTransfer(t: AccountTransfer, from: Option[CryptoCurrencyTransactionPort], to: Option[CryptoCurrencyTransactionPort], timestamp: Option[Long]) {}

  protected def isRetry(t: AccountTransfer): (Boolean, CryptoCurrencyTransferHandler) = {
    val existHandler = id2HandlerMap.values.filter(i => i.item.accountTransferId == Some(t.id)).toSeq
    existHandler.nonEmpty match {
      case true => (true, existHandler.head)
      case false => (false, null)
    }
  }

}

trait CryptoCurrencyTransferDepositLikeBase extends CryptoCurrencyTransferBase {
  val sigIdWithTxPort2HandlerMap = Map.empty[String, Map[CryptoCurrencyTransactionPort, CryptoCurrencyTransferHandler]]
  val REMOVE_OBSOLETE_TIME = 24 * 3600 * 1000L
  override def handleSucceeded(itemId: Long) {
    super.handleSucceeded(itemId)
    //if (id2HandlerMap.contains(itemId)) {
    // val item = id2HandlerMap(itemId).item
    // removeItemHandlerFromMap(item.sigId.get, item.to.get)
    //}
    removeObsoleteItems()
  }

  def removeObsoleteItems() {
    sigIdWithTxPort2HandlerMap.keys.foreach {
      sigId =>
        sigIdWithTxPort2HandlerMap(sigId).keys foreach {
          port =>
            val handler = sigIdWithTxPort2HandlerMap(sigId)(port)
            val created = handler.item.created.getOrElse(System.currentTimeMillis() - REMOVE_OBSOLETE_TIME)
            if ((handler.item.status == TransferStatus.Succeeded || handler.item.status == TransferStatus.ReorgingSucceeded)
              && System.currentTimeMillis - created >= REMOVE_OBSOLETE_TIME) {
              removeItemHandlerFromMap(sigId, port)
            }
        }
    }
  }

  override def handleFailed(handler: CryptoCurrencyTransferHandler, error: Option[ErrorCode] = None) {
    handler.onFail()
    id2HandlerMap.remove(handler.item.id)
    removeItemHandlerFromMap(handler.item.sigId.get, handler.item.to.get)
  }

  override def loadSnapshotItems(snapshotItems: Option[collection.Map[Long, CryptoCurrencyTransferItem]]) {
    super.loadSnapshotItems(snapshotItems)
    sigIdWithTxPort2HandlerMap.clear()
    if (snapshotItems.isDefined) {
      snapshotItems.get.values map {
        item =>
          if (!sigIdWithTxPort2HandlerMap.contains(item.sigId.get)) {
            sigIdWithTxPort2HandlerMap.put(item.sigId.get, Map.empty[CryptoCurrencyTransactionPort, CryptoCurrencyTransferHandler])
          }
          sigIdWithTxPort2HandlerMap(item.sigId.get).put(item.to.get, id2HandlerMap(item.id))
      }
    }
  }

  override def innerHandleTx(currency: Currency, tx: CryptoCurrencyTransaction, timestamp: Option[Long]) {
    tx.outputs match {
      case Some(outputList) if outputList.size > 0 =>
        val validOutputs = outputList filter (out => out.userId.isDefined && out.userId.get != COLD_UID)
        if (!validOutputs.isEmpty) {
          validOutputs foreach {
            //ColdToHot transfer should ignore cold outputPort
            outputPort =>
              tx.status match {
                case Failed =>
                  getItemHandlerFromMap(tx.sigId.get, outputPort) match {
                    case Some(handler) =>
                      handleFailed(handler.setTimeStamp(timestamp))
                    case None =>
                  }
                case _ =>
                  getItemHandlerFromMap(tx.sigId.get, outputPort) match {
                    case Some(handler) if handler.item.status == TransferStatus.Succeeded || handler.item.status == TransferStatus.ReorgingSucceeded =>
                      logger.info(s"succeeded deposit item meet tx again: ${tx.toString}, ${handler.item.toString}")
                    case Some(handler) =>
                      handler.setTimeStamp(timestamp).onNormal(tx)
                    case _ =>
                      val handler: Option[CryptoCurrencyTransferHandler] =
                        tx.txType match {
                          case Some(Deposit) =>
                            // Set minerFee to None, as Deposit should not handle it
                            Some(new CryptoCurrencyTransferDepositHandler(currency, outputPort, tx.copy(minerFee = None), timestamp))
                          case Some(ColdToHot) if outputPort.userId.get == HOT_UID =>
                            Some(new CryptoCurrencyTransferColdToHotHandler(currency, outputPort, tx, timestamp))
                          case Some(DepositHot) =>
                            Some(new CryptoCurrencyTransferDepositHotHandler(currency, outputPort, tx, timestamp))
                          case _ =>
                            logger.error(s"""${"~" * 50} innerHandleTx() ${tx.txType.get.toString} tx is not valid txType : ${tx.toString}""")
                            None
                        }
                      handler match {
                        case Some(hd) =>
                          saveItemHandlerToMap(tx.sigId.get, outputPort, hd)
                          id2HandlerMap.put(hd.item.id, hd)
                        case _ =>
                      }
                  }
              }
          }
          updateSigId2MinerFee(tx)
        } else {
          logger.warning(s"""${"~" * 50} ${currency.toString} innerHandleTx() ${tx.txType.get.toString} tx have no valid outputs : ${tx.toString}""")
        }
      case _ =>
        logger.warning(s"""${"~" * 50} ${currency.toString} innerHandleTx() ${tx.txType.get.toString} tx not define outputs : ${tx.toString}""")
    }
  }

  protected def removeItemHandlerFromMap(sigId: String, port: CryptoCurrencyTransactionPort) {
    if (sigIdWithTxPort2HandlerMap.contains(sigId)) {
      sigIdWithTxPort2HandlerMap(sigId).remove(port)
      if (sigIdWithTxPort2HandlerMap(sigId).isEmpty) {
        sigIdWithTxPort2HandlerMap.remove(sigId)
      }
    }
  }

  private def getItemHandlerFromMap(sigId: String, port: CryptoCurrencyTransactionPort): Option[CryptoCurrencyTransferHandler] = {
    if (sigIdWithTxPort2HandlerMap.contains(sigId) && sigIdWithTxPort2HandlerMap(sigId).contains(port))
      Some(sigIdWithTxPort2HandlerMap(sigId)(port))
    else
      None
  }

  private def saveItemHandlerToMap(sigId: String, port: CryptoCurrencyTransactionPort, handler: CryptoCurrencyTransferHandler) {
    if (!sigIdWithTxPort2HandlerMap.contains(sigId)) {
      sigIdWithTxPort2HandlerMap.put(sigId, Map.empty[CryptoCurrencyTransactionPort, CryptoCurrencyTransferHandler])
    }
    sigIdWithTxPort2HandlerMap(sigId).put(port, handler)
  }
}

trait CryptoCurrencyTransferWithdrawalLikeBase extends CryptoCurrencyTransferBase {

  override def newHandlerFromAccountTransfer(t: AccountTransfer, from: Option[CryptoCurrencyTransactionPort], to: Option[CryptoCurrencyTransactionPort], timestamp: Option[Long]) {
    isRetry(t) match {
      case (true, handler) =>
        handler.setTimeStamp(timestamp).retry()
        msgBoxMap.put(handler.item.id, handler.item)
      case (false, _) =>
        val handler = new CryptoCurrencyTransferWithdrawalLikeHandler(t, from, to, timestamp)
        msgBoxMap.put(handler.item.id, handler.item)
        id2HandlerMap.put(handler.item.id, handler)
    }
  }

  override def newHandlerFromItem(item: CryptoCurrencyTransferItem): CryptoCurrencyTransferHandler = {
    new CryptoCurrencyTransferWithdrawalLikeHandler(item)
  }

  override def handleFailed(handler: CryptoCurrencyTransferHandler, error: Option[ErrorCode] = None) {
    // withdrawal and hotToCold should do nothing when meet insufficient hot error
    if (error != Some(ErrorCode.InsufficientHot)) {
      val item = id2HandlerMap.remove(handler.item.id).get.item
      msgBoxMap.put(item.id, item)
    }
  }

  override def innerHandleTx(currency: Currency, tx: CryptoCurrencyTransaction, timestamp: Option[Long]) {
    tx.ids match {
      case Some(idList) if idList.size > 0 =>
        idList foreach {
          //every input corresponds to one tx
          id =>
            if (id2HandlerMap.contains(id)) {
              tx.status match {
                case Failed =>
                  handleFailed(id2HandlerMap(id).setTimeStamp(timestamp))
                case _ =>
                  id2HandlerMap(id).setTimeStamp(timestamp).onNormal(tx)
              }
            } else {
              logger.warning(s"""${"~" * 50} ${currency.toString} innerHandlerTx() ${tx.txType.get.toString} item id ${id} not contained in id2HandlerMap : ${tx.toString}""")
            }
        }
        updateSigId2MinerFee(tx)
      case _ =>
        logger.warning(s"""${"~" * 50} ${currency.toString} innerHandlerTx() ${tx.txType.get.toString} tx not define ids : ${tx.toString}""")
    }
  }
}
