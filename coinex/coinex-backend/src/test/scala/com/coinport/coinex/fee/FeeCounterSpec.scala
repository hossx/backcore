/**
 * Copyright 2014 Coinport Inc. All Rights Reserved.
 * Author: c@coinport.com (Chao Ma)
 */

package com.coinport.coinex.fee

import com.twitter.util.Eval
import java.io.File
import org.specs2.mutable._

import com.coinport.coinex.common.Constants._
import com.coinport.coinex.data._
import com.coinport.coinex.data.Currency._
import Implicits._

class FeeCounterSpec extends Specification {
  val feeConfig = FeeConfig(
    marketFeeRules = Map(
      (Btc ~> Rmb) -> PercentageFee(0.001),
      (Rmb ~> Btc) -> PercentageFee(0.001),
      (Pts ~> Rmb) -> PercentageFee(0.003),
      (Rmb ~> Pts) -> PercentageFee(0.003)),

    robotFeeRules = Map(
      TRAILING_STOP_ORDER_ROBOT_TYPE -> PercentageFee(0.003),
      STOP_ORDER_ROBOT_TYPE -> ConstantFee(10)),

    withdrawalFeeRules = Map(
      Btc -> ConstantFee(1),
      Rmb -> PercentageFee(0.002)))

  val feeCounter = new FeeCounter(feeConfig)

  "FeeCounter" should {
    val takerSide = Btc ~> Rmb
    val taker = Order(userId = 5, id = 5, price = Some(2000), quantity = 100000, timestamp = Some(0))
    val maker = Order(userId = 3, id = 3, price = Some(1.0 / 5000), quantity = 10000000, timestamp = Some(0))
    val updatedMaker = maker.copy(quantity = 0) // buy 2

    "transaction btc-rmb with 0.1% fee" in {
      val transaction = Transaction(50000, 0, takerSide, taker --> taker.copy(quantity = 98000), maker --> updatedMaker)
      val fees = feeCounter.count(transaction)
      fees mustEqual Seq(Fee(3, None, Btc, 2, None), Fee(5, None, Rmb, 10000, None))
      1 mustEqual 1
    }

    "transaction btc-pts with no fee" in {
      val transaction = Transaction(1, 1, (Btc ~> Pts), taker --> taker.copy(quantity = 98000), maker --> updatedMaker)
      val fees = feeCounter.count(transaction)
      fees mustEqual Seq.empty[Fee]
      1 mustEqual 1
    }

    "transaction btc-rmb with robot fee" in {
      val robotTaker = taker.copy(robotType = Some(STOP_ORDER_ROBOT_TYPE))
      val transaction = Transaction(1, 1, takerSide, robotTaker --> robotTaker.copy(quantity = 98000),
        maker.copy(robotType = Some(3)) --> updatedMaker.copy(robotType = Some(3)))
      val fees = feeCounter.count(transaction)
      fees mustEqual Seq(Fee(3, None, Btc, 2, None), Fee(5, None, Rmb, 10010, None))
    }

    "transaction btc-rmb with robot fee" in {
      val robotTaker = taker.copy(robotType = Some(STOP_ORDER_ROBOT_TYPE))
      val transaction = Transaction(1, 1, takerSide, robotTaker --> robotTaker.copy(quantity = 98000),
        maker.copy(robotType = Some(TRAILING_STOP_ORDER_ROBOT_TYPE)) -->
          updatedMaker.copy(robotType = Some(TRAILING_STOP_ORDER_ROBOT_TYPE)))
      val fees = feeCounter.count(transaction)
      fees mustEqual Seq(Fee(3, None, Btc, 8, None), Fee(5, None, Rmb, 10010, None))
    }
  }

  "FeeCounter" should {
    "withdrawal rmb with 0.2% fee" in {
      val withdrawal = Withdrawal(1, 2, Rmb, 12000, TransferStatus.Pending)
      val fees = feeCounter.count(withdrawal)
      fees mustEqual Seq(Fee(2, None, Rmb, 24, None))
    }

    "withdrawal btc with 1 fee" in {
      val w1 = Withdrawal(1, 2, Btc, 12000, TransferStatus.Pending)
      val fees1 = feeCounter.count(w1)
      fees1 mustEqual Seq(Fee(2, None, Btc, 1, None))

      val w2 = Withdrawal(1, 2, Btc, 12200, TransferStatus.Pending)
      val fees2 = feeCounter.count(w2)
      fees2 mustEqual Seq(Fee(2, None, Btc, 1, None))
    }

    "withdrawal pts with no result" in {
      val w = AdminConfirmCashWithdrawalSuccess(Withdrawal(1, 2, Pts, 12000, TransferStatus.Pending))
      val fees = feeCounter.count(w)
      fees mustEqual Seq.empty[Fee]
    }
  }
}