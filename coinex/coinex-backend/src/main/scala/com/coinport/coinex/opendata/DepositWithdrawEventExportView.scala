package com.coinport.coinex.opendata

import com.mongodb.casbah.MongoDB
import com.coinport.coinex.data._
import com.coinport.coinex.common.PersistentId._
import Implicits._

class DepositWithdrawEventExportView(db: MongoDB) extends EventExportToMongoView(db, DEPOSIT_WITHDRAW_PROCESSOR <<) {
  def shouldExport(event: AnyRef) = event match {
    case m: AdminConfirmCashDepositSuccess => true
    case m: AdminConfirmCashWithdrawalSuccess => true
    case _ => false
  }
}