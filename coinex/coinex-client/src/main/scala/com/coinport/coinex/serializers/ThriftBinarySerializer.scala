
/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 * This file was auto generated by auto_gen_serializer.sh on Sat Mar 29 16:13:39 CST 2014
 */

package com.coinport.coinex.serializers

import akka.serialization.Serializer
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.coinport.coinex.data._

class ThriftJsonSerializer extends Serializer {
  val includeManifest: Boolean = true
  val identifier = 607100416
  lazy val s_0 = JsonScalaCodec(UserProfile)
  lazy val s_1 = JsonScalaCodec(MarketSide)
  lazy val s_2 = JsonScalaCodec(Order)
  lazy val s_3 = JsonScalaCodec(OrderInfo)
  lazy val s_4 = JsonScalaCodec(OrderUpdate)
  lazy val s_5 = JsonScalaCodec(Transaction)
  lazy val s_6 = JsonScalaCodec(CashAccount)
  lazy val s_7 = JsonScalaCodec(UserAccount)
  lazy val s_8 = JsonScalaCodec(PersistentAccountState)
  lazy val s_9 = JsonScalaCodec(UserLogsState)
  lazy val s_10 = JsonScalaCodec(MarketDepthItem)
  lazy val s_11 = JsonScalaCodec(MarketDepth)
  lazy val s_12 = JsonScalaCodec(CandleDataItem)
  lazy val s_13 = JsonScalaCodec(CandleData)
  lazy val s_14 = JsonScalaCodec(MarketByMetrics)
  lazy val s_15 = JsonScalaCodec(RobotMetrics)
  lazy val s_16 = JsonScalaCodec(TransactionItem)
  lazy val s_17 = JsonScalaCodec(TransactionData)
  lazy val s_18 = JsonScalaCodec(ApiSecret)
  lazy val s_19 = JsonScalaCodec(ApiSecretState)
  lazy val s_20 = JsonScalaCodec(AdminCommandResult)
  lazy val s_21 = JsonScalaCodec(DoRegisterUser)
  lazy val s_22 = JsonScalaCodec(RegisterUserFailed)
  lazy val s_23 = JsonScalaCodec(RegisterUserSucceeded)
  lazy val s_24 = JsonScalaCodec(DoRequestPasswordReset)
  lazy val s_25 = JsonScalaCodec(RequestPasswordResetFailed)
  lazy val s_26 = JsonScalaCodec(RequestPasswordResetSucceeded)
  lazy val s_27 = JsonScalaCodec(DoResetPassword)
  lazy val s_28 = JsonScalaCodec(ResetPasswordFailed)
  lazy val s_29 = JsonScalaCodec(ResetPasswordSucceeded)
  lazy val s_30 = JsonScalaCodec(Login)
  lazy val s_31 = JsonScalaCodec(LoginFailed)
  lazy val s_32 = JsonScalaCodec(LoginSucceeded)
  lazy val s_33 = JsonScalaCodec(ValidatePasswordResetToken)
  lazy val s_34 = JsonScalaCodec(PasswordResetTokenValidationResult)
  lazy val s_35 = JsonScalaCodec(AccountOperationResult)
  lazy val s_36 = JsonScalaCodec(DoRequestCashDeposit)
  lazy val s_37 = JsonScalaCodec(RequestCashDepositFailed)
  lazy val s_38 = JsonScalaCodec(RequestCashDepositSucceeded)
  lazy val s_39 = JsonScalaCodec(DoRequestCashWithdrawal)
  lazy val s_40 = JsonScalaCodec(RequestCashWithdrawalFailed)
  lazy val s_41 = JsonScalaCodec(RequestCashWithdrawalSucceeded)
  lazy val s_42 = JsonScalaCodec(AdminConfirmCashDepositFailure)
  lazy val s_43 = JsonScalaCodec(AdminConfirmCashDepositSuccess)
  lazy val s_44 = JsonScalaCodec(AdminConfirmCashWithdrawalFailure)
  lazy val s_45 = JsonScalaCodec(AdminConfirmCashWithdrawalSuccess)
  lazy val s_46 = JsonScalaCodec(DoSubmitOrder)
  lazy val s_47 = JsonScalaCodec(SubmitOrderFailed)
  lazy val s_48 = JsonScalaCodec(OrderFundFrozen)
  lazy val s_49 = JsonScalaCodec(DoAddNewApiSecret)
  lazy val s_50 = JsonScalaCodec(DoDeleteApiSecret)
  lazy val s_51 = JsonScalaCodec(ApiSecretOperationResult)
  lazy val s_52 = JsonScalaCodec(QueryApiSecrets)
  lazy val s_53 = JsonScalaCodec(QueryApiSecretsResult)
  lazy val s_54 = JsonScalaCodec(DoCancelOrder)
  lazy val s_55 = JsonScalaCodec(CancelOrderFailed)
  lazy val s_56 = JsonScalaCodec(OrderSubmitted)
  lazy val s_57 = JsonScalaCodec(OrderCancelled)
  lazy val s_58 = JsonScalaCodec(SendMailRequest)
  lazy val s_59 = JsonScalaCodec(DoUpdateMetrics)
  lazy val s_60 = JsonScalaCodec(QueryAccount)
  lazy val s_61 = JsonScalaCodec(QueryAccountResult)
  lazy val s_62 = JsonScalaCodec(QueryMarketDepth)
  lazy val s_63 = JsonScalaCodec(QueryMarketDepthResult)
  lazy val s_64 = JsonScalaCodec(QueryCandleData)
  lazy val s_65 = JsonScalaCodec(QueryCandleDataResult)
  lazy val s_66 = JsonScalaCodec(QueryUserTransaction)
  lazy val s_67 = JsonScalaCodec(QueryUserTransactionResult)
  lazy val s_68 = JsonScalaCodec(QueryUserOrders)
  lazy val s_69 = JsonScalaCodec(QueryUserOrdersResult)
  lazy val s_70 = JsonScalaCodec(QueryTransactionData)
  lazy val s_71 = JsonScalaCodec(QueryTransactionDataResult)

  def toBinary(obj: AnyRef): Array[Byte] = obj match {
    case m: UserProfile => s_0(m)
    case m: MarketSide => s_1(m)
    case m: Order => s_2(m)
    case m: OrderInfo => s_3(m)
    case m: OrderUpdate => s_4(m)
    case m: Transaction => s_5(m)
    case m: CashAccount => s_6(m)
    case m: UserAccount => s_7(m)
    case m: PersistentAccountState => s_8(m)
    case m: UserLogsState => s_9(m)
    case m: MarketDepthItem => s_10(m)
    case m: MarketDepth => s_11(m)
    case m: CandleDataItem => s_12(m)
    case m: CandleData => s_13(m)
    case m: MarketByMetrics => s_14(m)
    case m: RobotMetrics => s_15(m)
    case m: TransactionItem => s_16(m)
    case m: TransactionData => s_17(m)
    case m: ApiSecret => s_18(m)
    case m: ApiSecretState => s_19(m)
    case m: AdminCommandResult => s_20(m)
    case m: DoRegisterUser => s_21(m)
    case m: RegisterUserFailed => s_22(m)
    case m: RegisterUserSucceeded => s_23(m)
    case m: DoRequestPasswordReset => s_24(m)
    case m: RequestPasswordResetFailed => s_25(m)
    case m: RequestPasswordResetSucceeded => s_26(m)
    case m: DoResetPassword => s_27(m)
    case m: ResetPasswordFailed => s_28(m)
    case m: ResetPasswordSucceeded => s_29(m)
    case m: Login => s_30(m)
    case m: LoginFailed => s_31(m)
    case m: LoginSucceeded => s_32(m)
    case m: ValidatePasswordResetToken => s_33(m)
    case m: PasswordResetTokenValidationResult => s_34(m)
    case m: AccountOperationResult => s_35(m)
    case m: DoRequestCashDeposit => s_36(m)
    case m: RequestCashDepositFailed => s_37(m)
    case m: RequestCashDepositSucceeded => s_38(m)
    case m: DoRequestCashWithdrawal => s_39(m)
    case m: RequestCashWithdrawalFailed => s_40(m)
    case m: RequestCashWithdrawalSucceeded => s_41(m)
    case m: AdminConfirmCashDepositFailure => s_42(m)
    case m: AdminConfirmCashDepositSuccess => s_43(m)
    case m: AdminConfirmCashWithdrawalFailure => s_44(m)
    case m: AdminConfirmCashWithdrawalSuccess => s_45(m)
    case m: DoSubmitOrder => s_46(m)
    case m: SubmitOrderFailed => s_47(m)
    case m: OrderFundFrozen => s_48(m)
    case m: DoAddNewApiSecret => s_49(m)
    case m: DoDeleteApiSecret => s_50(m)
    case m: ApiSecretOperationResult => s_51(m)
    case m: QueryApiSecrets => s_52(m)
    case m: QueryApiSecretsResult => s_53(m)
    case m: DoCancelOrder => s_54(m)
    case m: CancelOrderFailed => s_55(m)
    case m: OrderSubmitted => s_56(m)
    case m: OrderCancelled => s_57(m)
    case m: SendMailRequest => s_58(m)
    case m: DoUpdateMetrics => s_59(m)
    case m: QueryAccount => s_60(m)
    case m: QueryAccountResult => s_61(m)
    case m: QueryMarketDepth => s_62(m)
    case m: QueryMarketDepthResult => s_63(m)
    case m: QueryCandleData => s_64(m)
    case m: QueryCandleDataResult => s_65(m)
    case m: QueryUserTransaction => s_66(m)
    case m: QueryUserTransactionResult => s_67(m)
    case m: QueryUserOrders => s_68(m)
    case m: QueryUserOrdersResult => s_69(m)
    case m: QueryTransactionData => s_70(m)
    case m: QueryTransactionDataResult => s_71(m)

    case m => throw new IllegalArgumentException("Cannot serialize object: " + m)
  }

  def fromBinary(bytes: Array[Byte],
    clazz: Option[Class[_]]): AnyRef = clazz match {
    case Some(c) if c == classOf[UserProfile.Immutable] => s_0.invert(bytes).get
    case Some(c) if c == classOf[MarketSide.Immutable] => s_1.invert(bytes).get
    case Some(c) if c == classOf[Order.Immutable] => s_2.invert(bytes).get
    case Some(c) if c == classOf[OrderInfo.Immutable] => s_3.invert(bytes).get
    case Some(c) if c == classOf[OrderUpdate.Immutable] => s_4.invert(bytes).get
    case Some(c) if c == classOf[Transaction.Immutable] => s_5.invert(bytes).get
    case Some(c) if c == classOf[CashAccount.Immutable] => s_6.invert(bytes).get
    case Some(c) if c == classOf[UserAccount.Immutable] => s_7.invert(bytes).get
    case Some(c) if c == classOf[PersistentAccountState.Immutable] => s_8.invert(bytes).get
    case Some(c) if c == classOf[UserLogsState.Immutable] => s_9.invert(bytes).get
    case Some(c) if c == classOf[MarketDepthItem.Immutable] => s_10.invert(bytes).get
    case Some(c) if c == classOf[MarketDepth.Immutable] => s_11.invert(bytes).get
    case Some(c) if c == classOf[CandleDataItem.Immutable] => s_12.invert(bytes).get
    case Some(c) if c == classOf[CandleData.Immutable] => s_13.invert(bytes).get
    case Some(c) if c == classOf[MarketByMetrics.Immutable] => s_14.invert(bytes).get
    case Some(c) if c == classOf[RobotMetrics.Immutable] => s_15.invert(bytes).get
    case Some(c) if c == classOf[TransactionItem.Immutable] => s_16.invert(bytes).get
    case Some(c) if c == classOf[TransactionData.Immutable] => s_17.invert(bytes).get
    case Some(c) if c == classOf[ApiSecret.Immutable] => s_18.invert(bytes).get
    case Some(c) if c == classOf[ApiSecretState.Immutable] => s_19.invert(bytes).get
    case Some(c) if c == classOf[AdminCommandResult.Immutable] => s_20.invert(bytes).get
    case Some(c) if c == classOf[DoRegisterUser.Immutable] => s_21.invert(bytes).get
    case Some(c) if c == classOf[RegisterUserFailed.Immutable] => s_22.invert(bytes).get
    case Some(c) if c == classOf[RegisterUserSucceeded.Immutable] => s_23.invert(bytes).get
    case Some(c) if c == classOf[DoRequestPasswordReset.Immutable] => s_24.invert(bytes).get
    case Some(c) if c == classOf[RequestPasswordResetFailed.Immutable] => s_25.invert(bytes).get
    case Some(c) if c == classOf[RequestPasswordResetSucceeded.Immutable] => s_26.invert(bytes).get
    case Some(c) if c == classOf[DoResetPassword.Immutable] => s_27.invert(bytes).get
    case Some(c) if c == classOf[ResetPasswordFailed.Immutable] => s_28.invert(bytes).get
    case Some(c) if c == classOf[ResetPasswordSucceeded.Immutable] => s_29.invert(bytes).get
    case Some(c) if c == classOf[Login.Immutable] => s_30.invert(bytes).get
    case Some(c) if c == classOf[LoginFailed.Immutable] => s_31.invert(bytes).get
    case Some(c) if c == classOf[LoginSucceeded.Immutable] => s_32.invert(bytes).get
    case Some(c) if c == classOf[ValidatePasswordResetToken.Immutable] => s_33.invert(bytes).get
    case Some(c) if c == classOf[PasswordResetTokenValidationResult.Immutable] => s_34.invert(bytes).get
    case Some(c) if c == classOf[AccountOperationResult.Immutable] => s_35.invert(bytes).get
    case Some(c) if c == classOf[DoRequestCashDeposit.Immutable] => s_36.invert(bytes).get
    case Some(c) if c == classOf[RequestCashDepositFailed.Immutable] => s_37.invert(bytes).get
    case Some(c) if c == classOf[RequestCashDepositSucceeded.Immutable] => s_38.invert(bytes).get
    case Some(c) if c == classOf[DoRequestCashWithdrawal.Immutable] => s_39.invert(bytes).get
    case Some(c) if c == classOf[RequestCashWithdrawalFailed.Immutable] => s_40.invert(bytes).get
    case Some(c) if c == classOf[RequestCashWithdrawalSucceeded.Immutable] => s_41.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmCashDepositFailure.Immutable] => s_42.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmCashDepositSuccess.Immutable] => s_43.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmCashWithdrawalFailure.Immutable] => s_44.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmCashWithdrawalSuccess.Immutable] => s_45.invert(bytes).get
    case Some(c) if c == classOf[DoSubmitOrder.Immutable] => s_46.invert(bytes).get
    case Some(c) if c == classOf[SubmitOrderFailed.Immutable] => s_47.invert(bytes).get
    case Some(c) if c == classOf[OrderFundFrozen.Immutable] => s_48.invert(bytes).get
    case Some(c) if c == classOf[DoAddNewApiSecret.Immutable] => s_49.invert(bytes).get
    case Some(c) if c == classOf[DoDeleteApiSecret.Immutable] => s_50.invert(bytes).get
    case Some(c) if c == classOf[ApiSecretOperationResult.Immutable] => s_51.invert(bytes).get
    case Some(c) if c == classOf[QueryApiSecrets.Immutable] => s_52.invert(bytes).get
    case Some(c) if c == classOf[QueryApiSecretsResult.Immutable] => s_53.invert(bytes).get
    case Some(c) if c == classOf[DoCancelOrder.Immutable] => s_54.invert(bytes).get
    case Some(c) if c == classOf[CancelOrderFailed.Immutable] => s_55.invert(bytes).get
    case Some(c) if c == classOf[OrderSubmitted.Immutable] => s_56.invert(bytes).get
    case Some(c) if c == classOf[OrderCancelled.Immutable] => s_57.invert(bytes).get
    case Some(c) if c == classOf[SendMailRequest.Immutable] => s_58.invert(bytes).get
    case Some(c) if c == classOf[DoUpdateMetrics.Immutable] => s_59.invert(bytes).get
    case Some(c) if c == classOf[QueryAccount.Immutable] => s_60.invert(bytes).get
    case Some(c) if c == classOf[QueryAccountResult.Immutable] => s_61.invert(bytes).get
    case Some(c) if c == classOf[QueryMarketDepth.Immutable] => s_62.invert(bytes).get
    case Some(c) if c == classOf[QueryMarketDepthResult.Immutable] => s_63.invert(bytes).get
    case Some(c) if c == classOf[QueryCandleData.Immutable] => s_64.invert(bytes).get
    case Some(c) if c == classOf[QueryCandleDataResult.Immutable] => s_65.invert(bytes).get
    case Some(c) if c == classOf[QueryUserTransaction.Immutable] => s_66.invert(bytes).get
    case Some(c) if c == classOf[QueryUserTransactionResult.Immutable] => s_67.invert(bytes).get
    case Some(c) if c == classOf[QueryUserOrders.Immutable] => s_68.invert(bytes).get
    case Some(c) if c == classOf[QueryUserOrdersResult.Immutable] => s_69.invert(bytes).get
    case Some(c) if c == classOf[QueryTransactionData.Immutable] => s_70.invert(bytes).get
    case Some(c) if c == classOf[QueryTransactionDataResult.Immutable] => s_71.invert(bytes).get

    case Some(c) => throw new IllegalArgumentException("Cannot deserialize class: " + c.getCanonicalName)
    case None => throw new IllegalArgumentException("No class found in EventSerializer when deserializing array: " + bytes.mkString("").take(100))
  }
}
