
/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 * This file was auto generated by auto_gen_serializer.sh
 */

package com.coinport.coinex.serializers

import akka.serialization.Serializer
import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.coinport.coinex.data._

class ThriftJsonSerializer extends Serializer {
  val includeManifest: Boolean = true
  val identifier = 607100416
  lazy val _cABCodeItem = JsonScalaCodec(ABCodeItem)
  lazy val _cAccountTransfer = JsonScalaCodec(AccountTransfer)
  lazy val _cAddressStatusResult = JsonScalaCodec(AddressStatusResult)
  lazy val _cApiSecret = JsonScalaCodec(ApiSecret)
  lazy val _cBlockIndex = JsonScalaCodec(BlockIndex)
  lazy val _cCandleData = JsonScalaCodec(CandleData)
  lazy val _cCandleDataItem = JsonScalaCodec(CandleDataItem)
  lazy val _cCashAccount = JsonScalaCodec(CashAccount)
  lazy val _cCryptoCurrencyBlock = JsonScalaCodec(CryptoCurrencyBlock)
  lazy val _cCryptoCurrencyNetworkStatus = JsonScalaCodec(CryptoCurrencyNetworkStatus)
  lazy val _cCryptoCurrencyTransaction = JsonScalaCodec(CryptoCurrencyTransaction)
  lazy val _cCryptoCurrencyTransactionPort = JsonScalaCodec(CryptoCurrencyTransactionPort)
  lazy val _cCryptoCurrencyTransferInfo = JsonScalaCodec(CryptoCurrencyTransferInfo)
  lazy val _cCryptoCurrencyTransferItem = JsonScalaCodec(CryptoCurrencyTransferItem)
  lazy val _cCurrentAsset = JsonScalaCodec(CurrentAsset)
  lazy val _cCurrentPrice = JsonScalaCodec(CurrentPrice)
  lazy val _cCursor = JsonScalaCodec(Cursor)
  lazy val _cExportOpenDataMap = JsonScalaCodec(ExportOpenDataMap)
  lazy val _cFee = JsonScalaCodec(Fee)
  lazy val _cHistoryAsset = JsonScalaCodec(HistoryAsset)
  lazy val _cHistoryPrice = JsonScalaCodec(HistoryPrice)
  lazy val _cMarketDepth = JsonScalaCodec(MarketDepth)
  lazy val _cMarketDepthItem = JsonScalaCodec(MarketDepthItem)
  lazy val _cMarketEvent = JsonScalaCodec(MarketEvent)
  lazy val _cMarketSide = JsonScalaCodec(MarketSide)
  lazy val _cMetrics = JsonScalaCodec(Metrics)
  lazy val _cMetricsByMarket = JsonScalaCodec(MetricsByMarket)
  lazy val _cNotification = JsonScalaCodec(Notification)
  lazy val _cOrder = JsonScalaCodec(Order)
  lazy val _cOrderInfo = JsonScalaCodec(OrderInfo)
  lazy val _cOrderUpdate = JsonScalaCodec(OrderUpdate)
  lazy val _cQueryMarketSide = JsonScalaCodec(QueryMarketSide)
  lazy val _cRDouble = JsonScalaCodec(RDouble)
  lazy val _cRedeliverFilterData = JsonScalaCodec(RedeliverFilterData)
  lazy val _cRedeliverFilters = JsonScalaCodec(RedeliverFilters)
  lazy val _cRefund = JsonScalaCodec(Refund)
  lazy val _cSpanCursor = JsonScalaCodec(SpanCursor)
  lazy val _cTAddressStatus = JsonScalaCodec(TAddressStatus)
  lazy val _cTMetricsObserver = JsonScalaCodec(TMetricsObserver)
  lazy val _cTRobot = JsonScalaCodec(TRobot)
  lazy val _cTStackQueue = JsonScalaCodec(TStackQueue)
  lazy val _cTWindowQueue = JsonScalaCodec(TWindowQueue)
  lazy val _cTWindowVector = JsonScalaCodec(TWindowVector)
  lazy val _cTransaction = JsonScalaCodec(Transaction)
  lazy val _cUserAccount = JsonScalaCodec(UserAccount)
  lazy val _cUserLogsState = JsonScalaCodec(UserLogsState)
  lazy val _cUserProfile = JsonScalaCodec(UserProfile)
  lazy val _cAddRobotDNAFailed = JsonScalaCodec(AddRobotDNAFailed)
  lazy val _cAddRobotDNASucceeded = JsonScalaCodec(AddRobotDNASucceeded)
  lazy val _cAdminCommandResult = JsonScalaCodec(AdminCommandResult)
  lazy val _cAdminConfirmTransferFailure = JsonScalaCodec(AdminConfirmTransferFailure)
  lazy val _cAdminConfirmTransferSuccess = JsonScalaCodec(AdminConfirmTransferSuccess)
  lazy val _cAllocateNewAddress = JsonScalaCodec(AllocateNewAddress)
  lazy val _cAllocateNewAddressResult = JsonScalaCodec(AllocateNewAddressResult)
  lazy val _cApiSecretOperationResult = JsonScalaCodec(ApiSecretOperationResult)
  lazy val _cBitwayMessage = JsonScalaCodec(BitwayMessage)
  lazy val _cBitwayRequest = JsonScalaCodec(BitwayRequest)
  lazy val _cCancelOrderFailed = JsonScalaCodec(CancelOrderFailed)
  lazy val _cCryptoCurrencyBlocksMessage = JsonScalaCodec(CryptoCurrencyBlocksMessage)
  lazy val _cCryptoTransferFailed = JsonScalaCodec(CryptoTransferFailed)
  lazy val _cCryptoTransferSucceeded = JsonScalaCodec(CryptoTransferSucceeded)
  lazy val _cDoAddNewApiSecret = JsonScalaCodec(DoAddNewApiSecret)
  lazy val _cDoAddRobotDNA = JsonScalaCodec(DoAddRobotDNA)
  lazy val _cDoCancelOrder = JsonScalaCodec(DoCancelOrder)
  lazy val _cDoCancelTransfer = JsonScalaCodec(DoCancelTransfer)
  lazy val _cDoDeleteApiSecret = JsonScalaCodec(DoDeleteApiSecret)
  lazy val _cDoRegisterUser = JsonScalaCodec(DoRegisterUser)
  lazy val _cDoRemoveRobotDNA = JsonScalaCodec(DoRemoveRobotDNA)
  lazy val _cDoRequestACodeQuery = JsonScalaCodec(DoRequestACodeQuery)
  lazy val _cDoRequestBCodeRecharge = JsonScalaCodec(DoRequestBCodeRecharge)
  lazy val _cDoRequestConfirmRC = JsonScalaCodec(DoRequestConfirmRC)
  lazy val _cDoRequestGenerateABCode = JsonScalaCodec(DoRequestGenerateABCode)
  lazy val _cDoRequestPasswordReset = JsonScalaCodec(DoRequestPasswordReset)
  lazy val _cDoRequestTransfer = JsonScalaCodec(DoRequestTransfer)
  lazy val _cDoResetPassword = JsonScalaCodec(DoResetPassword)
  lazy val _cDoSendEmail = JsonScalaCodec(DoSendEmail)
  lazy val _cDoSimulateOrderSubmission = JsonScalaCodec(DoSimulateOrderSubmission)
  lazy val _cDoSubmitOrder = JsonScalaCodec(DoSubmitOrder)
  lazy val _cDoUpdateMetrics = JsonScalaCodec(DoUpdateMetrics)
  lazy val _cDoUpdateUserProfile = JsonScalaCodec(DoUpdateUserProfile)
  lazy val _cDumpStateToFile = JsonScalaCodec(DumpStateToFile)
  lazy val _cGenerateAddresses = JsonScalaCodec(GenerateAddresses)
  lazy val _cGenerateAddressesResult = JsonScalaCodec(GenerateAddressesResult)
  lazy val _cGetMissedCryptoCurrencyBlocks = JsonScalaCodec(GetMissedCryptoCurrencyBlocks)
  lazy val _cGoogleAuthCodeVerificationResult = JsonScalaCodec(GoogleAuthCodeVerificationResult)
  lazy val _cLogin = JsonScalaCodec(Login)
  lazy val _cLoginFailed = JsonScalaCodec(LoginFailed)
  lazy val _cLoginSucceeded = JsonScalaCodec(LoginSucceeded)
  lazy val _cMessageNotSupported = JsonScalaCodec(MessageNotSupported)
  lazy val _cMultiCryptoCurrencyTransactionMessage = JsonScalaCodec(MultiCryptoCurrencyTransactionMessage)
  lazy val _cOrderCancelled = JsonScalaCodec(OrderCancelled)
  lazy val _cOrderFundFrozen = JsonScalaCodec(OrderFundFrozen)
  lazy val _cOrderSubmissionSimulated = JsonScalaCodec(OrderSubmissionSimulated)
  lazy val _cOrderSubmitted = JsonScalaCodec(OrderSubmitted)
  lazy val _cPasswordResetTokenValidationResult = JsonScalaCodec(PasswordResetTokenValidationResult)
  lazy val _cQueryAccount = JsonScalaCodec(QueryAccount)
  lazy val _cQueryAccountResult = JsonScalaCodec(QueryAccountResult)
  lazy val _cQueryAccountStatistics = JsonScalaCodec(QueryAccountStatistics)
  lazy val _cQueryAccountStatisticsResult = JsonScalaCodec(QueryAccountStatisticsResult)
  lazy val _cQueryApiSecrets = JsonScalaCodec(QueryApiSecrets)
  lazy val _cQueryApiSecretsResult = JsonScalaCodec(QueryApiSecretsResult)
  lazy val _cQueryAsset = JsonScalaCodec(QueryAsset)
  lazy val _cQueryAssetResult = JsonScalaCodec(QueryAssetResult)
  lazy val _cQueryCandleData = JsonScalaCodec(QueryCandleData)
  lazy val _cQueryCandleDataResult = JsonScalaCodec(QueryCandleDataResult)
  lazy val _cQueryCryptoCurrencyAddressStatus = JsonScalaCodec(QueryCryptoCurrencyAddressStatus)
  lazy val _cQueryCryptoCurrencyAddressStatusResult = JsonScalaCodec(QueryCryptoCurrencyAddressStatusResult)
  lazy val _cQueryCryptoCurrencyNetworkStatus = JsonScalaCodec(QueryCryptoCurrencyNetworkStatus)
  lazy val _cQueryCryptoCurrencyNetworkStatusResult = JsonScalaCodec(QueryCryptoCurrencyNetworkStatusResult)
  lazy val _cQueryCryptoCurrencyTransfer = JsonScalaCodec(QueryCryptoCurrencyTransfer)
  lazy val _cQueryCryptoCurrencyTransferResult = JsonScalaCodec(QueryCryptoCurrencyTransferResult)
  lazy val _cQueryMarketDepth = JsonScalaCodec(QueryMarketDepth)
  lazy val _cQueryMarketDepthResult = JsonScalaCodec(QueryMarketDepthResult)
  lazy val _cQueryNotification = JsonScalaCodec(QueryNotification)
  lazy val _cQueryNotificationResult = JsonScalaCodec(QueryNotificationResult)
  lazy val _cQueryOrder = JsonScalaCodec(QueryOrder)
  lazy val _cQueryOrderResult = JsonScalaCodec(QueryOrderResult)
  lazy val _cQueryProfile = JsonScalaCodec(QueryProfile)
  lazy val _cQueryProfileResult = JsonScalaCodec(QueryProfileResult)
  lazy val _cQueryRCDepositRecord = JsonScalaCodec(QueryRCDepositRecord)
  lazy val _cQueryRCDepositRecordResult = JsonScalaCodec(QueryRCDepositRecordResult)
  lazy val _cQueryRCWithdrawalRecord = JsonScalaCodec(QueryRCWithdrawalRecord)
  lazy val _cQueryRCWithdrawalRecordResult = JsonScalaCodec(QueryRCWithdrawalRecordResult)
  lazy val _cQueryTransaction = JsonScalaCodec(QueryTransaction)
  lazy val _cQueryTransactionResult = JsonScalaCodec(QueryTransactionResult)
  lazy val _cQueryTransfer = JsonScalaCodec(QueryTransfer)
  lazy val _cQueryTransferResult = JsonScalaCodec(QueryTransferResult)
  lazy val _cRegisterUserFailed = JsonScalaCodec(RegisterUserFailed)
  lazy val _cRegisterUserSucceeded = JsonScalaCodec(RegisterUserSucceeded)
  lazy val _cRemoveRobotDNAFailed = JsonScalaCodec(RemoveRobotDNAFailed)
  lazy val _cRemoveRobotDNASucceeded = JsonScalaCodec(RemoveRobotDNASucceeded)
  lazy val _cRequestACodeQueryFailed = JsonScalaCodec(RequestACodeQueryFailed)
  lazy val _cRequestACodeQuerySucceeded = JsonScalaCodec(RequestACodeQuerySucceeded)
  lazy val _cRequestBCodeRechargeFailed = JsonScalaCodec(RequestBCodeRechargeFailed)
  lazy val _cRequestBCodeRechargeSucceeded = JsonScalaCodec(RequestBCodeRechargeSucceeded)
  lazy val _cRequestConfirmRCFailed = JsonScalaCodec(RequestConfirmRCFailed)
  lazy val _cRequestConfirmRCSucceeded = JsonScalaCodec(RequestConfirmRCSucceeded)
  lazy val _cRequestGenerateABCodeFailed = JsonScalaCodec(RequestGenerateABCodeFailed)
  lazy val _cRequestGenerateABCodeSucceeded = JsonScalaCodec(RequestGenerateABCodeSucceeded)
  lazy val _cRequestPasswordResetFailed = JsonScalaCodec(RequestPasswordResetFailed)
  lazy val _cRequestPasswordResetSucceeded = JsonScalaCodec(RequestPasswordResetSucceeded)
  lazy val _cRequestTransferFailed = JsonScalaCodec(RequestTransferFailed)
  lazy val _cRequestTransferSucceeded = JsonScalaCodec(RequestTransferSucceeded)
  lazy val _cResetPasswordFailed = JsonScalaCodec(ResetPasswordFailed)
  lazy val _cResetPasswordSucceeded = JsonScalaCodec(ResetPasswordSucceeded)
  lazy val _cSetNotification = JsonScalaCodec(SetNotification)
  lazy val _cSubmitOrderFailed = JsonScalaCodec(SubmitOrderFailed)
  lazy val _cTakeSnapshotNow = JsonScalaCodec(TakeSnapshotNow)
  lazy val _cTransferCryptoCurrency = JsonScalaCodec(TransferCryptoCurrency)
  lazy val _cTransferCryptoCurrencyResult = JsonScalaCodec(TransferCryptoCurrencyResult)
  lazy val _cUpdateUserProfileFailed = JsonScalaCodec(UpdateUserProfileFailed)
  lazy val _cUpdateUserProfileSucceeded = JsonScalaCodec(UpdateUserProfileSucceeded)
  lazy val _cValidatePasswordResetToken = JsonScalaCodec(ValidatePasswordResetToken)
  lazy val _cVerifyEmail = JsonScalaCodec(VerifyEmail)
  lazy val _cVerifyEmailFailed = JsonScalaCodec(VerifyEmailFailed)
  lazy val _cVerifyEmailSucceeded = JsonScalaCodec(VerifyEmailSucceeded)
  lazy val _cVerifyGoogleAuthCode = JsonScalaCodec(VerifyGoogleAuthCode)
  lazy val _cTAccountState = JsonScalaCodec(TAccountState)
  lazy val _cTAccountTransferState = JsonScalaCodec(TAccountTransferState)
  lazy val _cTApiSecretState = JsonScalaCodec(TApiSecretState)
  lazy val _cTAssetState = JsonScalaCodec(TAssetState)
  lazy val _cTBitwayState = JsonScalaCodec(TBitwayState)
  lazy val _cTCandleDataState = JsonScalaCodec(TCandleDataState)
  lazy val _cTMarketState = JsonScalaCodec(TMarketState)
  lazy val _cTMetricsState = JsonScalaCodec(TMetricsState)
  lazy val _cTRobotState = JsonScalaCodec(TRobotState)
  lazy val _cTSimpleState = JsonScalaCodec(TSimpleState)
  lazy val _cTUserState = JsonScalaCodec(TUserState)

  def toBinary(obj: AnyRef): Array[Byte] = obj match {
    case m: ABCodeItem => _cABCodeItem(m)
    case m: AccountTransfer => _cAccountTransfer(m)
    case m: AddressStatusResult => _cAddressStatusResult(m)
    case m: ApiSecret => _cApiSecret(m)
    case m: BlockIndex => _cBlockIndex(m)
    case m: CandleData => _cCandleData(m)
    case m: CandleDataItem => _cCandleDataItem(m)
    case m: CashAccount => _cCashAccount(m)
    case m: CryptoCurrencyBlock => _cCryptoCurrencyBlock(m)
    case m: CryptoCurrencyNetworkStatus => _cCryptoCurrencyNetworkStatus(m)
    case m: CryptoCurrencyTransaction => _cCryptoCurrencyTransaction(m)
    case m: CryptoCurrencyTransactionPort => _cCryptoCurrencyTransactionPort(m)
    case m: CryptoCurrencyTransferInfo => _cCryptoCurrencyTransferInfo(m)
    case m: CryptoCurrencyTransferItem => _cCryptoCurrencyTransferItem(m)
    case m: CurrentAsset => _cCurrentAsset(m)
    case m: CurrentPrice => _cCurrentPrice(m)
    case m: Cursor => _cCursor(m)
    case m: ExportOpenDataMap => _cExportOpenDataMap(m)
    case m: Fee => _cFee(m)
    case m: HistoryAsset => _cHistoryAsset(m)
    case m: HistoryPrice => _cHistoryPrice(m)
    case m: MarketDepth => _cMarketDepth(m)
    case m: MarketDepthItem => _cMarketDepthItem(m)
    case m: MarketEvent => _cMarketEvent(m)
    case m: MarketSide => _cMarketSide(m)
    case m: Metrics => _cMetrics(m)
    case m: MetricsByMarket => _cMetricsByMarket(m)
    case m: Notification => _cNotification(m)
    case m: Order => _cOrder(m)
    case m: OrderInfo => _cOrderInfo(m)
    case m: OrderUpdate => _cOrderUpdate(m)
    case m: QueryMarketSide => _cQueryMarketSide(m)
    case m: RDouble => _cRDouble(m)
    case m: RedeliverFilterData => _cRedeliverFilterData(m)
    case m: RedeliverFilters => _cRedeliverFilters(m)
    case m: Refund => _cRefund(m)
    case m: SpanCursor => _cSpanCursor(m)
    case m: TAddressStatus => _cTAddressStatus(m)
    case m: TMetricsObserver => _cTMetricsObserver(m)
    case m: TRobot => _cTRobot(m)
    case m: TStackQueue => _cTStackQueue(m)
    case m: TWindowQueue => _cTWindowQueue(m)
    case m: TWindowVector => _cTWindowVector(m)
    case m: Transaction => _cTransaction(m)
    case m: UserAccount => _cUserAccount(m)
    case m: UserLogsState => _cUserLogsState(m)
    case m: UserProfile => _cUserProfile(m)
    case m: AddRobotDNAFailed => _cAddRobotDNAFailed(m)
    case m: AddRobotDNASucceeded => _cAddRobotDNASucceeded(m)
    case m: AdminCommandResult => _cAdminCommandResult(m)
    case m: AdminConfirmTransferFailure => _cAdminConfirmTransferFailure(m)
    case m: AdminConfirmTransferSuccess => _cAdminConfirmTransferSuccess(m)
    case m: AllocateNewAddress => _cAllocateNewAddress(m)
    case m: AllocateNewAddressResult => _cAllocateNewAddressResult(m)
    case m: ApiSecretOperationResult => _cApiSecretOperationResult(m)
    case m: BitwayMessage => _cBitwayMessage(m)
    case m: BitwayRequest => _cBitwayRequest(m)
    case m: CancelOrderFailed => _cCancelOrderFailed(m)
    case m: CryptoCurrencyBlocksMessage => _cCryptoCurrencyBlocksMessage(m)
    case m: CryptoTransferFailed => _cCryptoTransferFailed(m)
    case m: CryptoTransferSucceeded => _cCryptoTransferSucceeded(m)
    case m: DoAddNewApiSecret => _cDoAddNewApiSecret(m)
    case m: DoAddRobotDNA => _cDoAddRobotDNA(m)
    case m: DoCancelOrder => _cDoCancelOrder(m)
    case m: DoCancelTransfer => _cDoCancelTransfer(m)
    case m: DoDeleteApiSecret => _cDoDeleteApiSecret(m)
    case m: DoRegisterUser => _cDoRegisterUser(m)
    case m: DoRemoveRobotDNA => _cDoRemoveRobotDNA(m)
    case m: DoRequestACodeQuery => _cDoRequestACodeQuery(m)
    case m: DoRequestBCodeRecharge => _cDoRequestBCodeRecharge(m)
    case m: DoRequestConfirmRC => _cDoRequestConfirmRC(m)
    case m: DoRequestGenerateABCode => _cDoRequestGenerateABCode(m)
    case m: DoRequestPasswordReset => _cDoRequestPasswordReset(m)
    case m: DoRequestTransfer => _cDoRequestTransfer(m)
    case m: DoResetPassword => _cDoResetPassword(m)
    case m: DoSendEmail => _cDoSendEmail(m)
    case m: DoSimulateOrderSubmission => _cDoSimulateOrderSubmission(m)
    case m: DoSubmitOrder => _cDoSubmitOrder(m)
    case m: DoUpdateMetrics => _cDoUpdateMetrics(m)
    case m: DoUpdateUserProfile => _cDoUpdateUserProfile(m)
    case m: DumpStateToFile => _cDumpStateToFile(m)
    case m: GenerateAddresses => _cGenerateAddresses(m)
    case m: GenerateAddressesResult => _cGenerateAddressesResult(m)
    case m: GetMissedCryptoCurrencyBlocks => _cGetMissedCryptoCurrencyBlocks(m)
    case m: GoogleAuthCodeVerificationResult => _cGoogleAuthCodeVerificationResult(m)
    case m: Login => _cLogin(m)
    case m: LoginFailed => _cLoginFailed(m)
    case m: LoginSucceeded => _cLoginSucceeded(m)
    case m: MessageNotSupported => _cMessageNotSupported(m)
    case m: MultiCryptoCurrencyTransactionMessage => _cMultiCryptoCurrencyTransactionMessage(m)
    case m: OrderCancelled => _cOrderCancelled(m)
    case m: OrderFundFrozen => _cOrderFundFrozen(m)
    case m: OrderSubmissionSimulated => _cOrderSubmissionSimulated(m)
    case m: OrderSubmitted => _cOrderSubmitted(m)
    case m: PasswordResetTokenValidationResult => _cPasswordResetTokenValidationResult(m)
    case m: QueryAccount => _cQueryAccount(m)
    case m: QueryAccountResult => _cQueryAccountResult(m)
    case m: QueryAccountStatistics => _cQueryAccountStatistics(m)
    case m: QueryAccountStatisticsResult => _cQueryAccountStatisticsResult(m)
    case m: QueryApiSecrets => _cQueryApiSecrets(m)
    case m: QueryApiSecretsResult => _cQueryApiSecretsResult(m)
    case m: QueryAsset => _cQueryAsset(m)
    case m: QueryAssetResult => _cQueryAssetResult(m)
    case m: QueryCandleData => _cQueryCandleData(m)
    case m: QueryCandleDataResult => _cQueryCandleDataResult(m)
    case m: QueryCryptoCurrencyAddressStatus => _cQueryCryptoCurrencyAddressStatus(m)
    case m: QueryCryptoCurrencyAddressStatusResult => _cQueryCryptoCurrencyAddressStatusResult(m)
    case m: QueryCryptoCurrencyNetworkStatus => _cQueryCryptoCurrencyNetworkStatus(m)
    case m: QueryCryptoCurrencyNetworkStatusResult => _cQueryCryptoCurrencyNetworkStatusResult(m)
    case m: QueryCryptoCurrencyTransfer => _cQueryCryptoCurrencyTransfer(m)
    case m: QueryCryptoCurrencyTransferResult => _cQueryCryptoCurrencyTransferResult(m)
    case m: QueryMarketDepth => _cQueryMarketDepth(m)
    case m: QueryMarketDepthResult => _cQueryMarketDepthResult(m)
    case m: QueryNotification => _cQueryNotification(m)
    case m: QueryNotificationResult => _cQueryNotificationResult(m)
    case m: QueryOrder => _cQueryOrder(m)
    case m: QueryOrderResult => _cQueryOrderResult(m)
    case m: QueryProfile => _cQueryProfile(m)
    case m: QueryProfileResult => _cQueryProfileResult(m)
    case m: QueryRCDepositRecord => _cQueryRCDepositRecord(m)
    case m: QueryRCDepositRecordResult => _cQueryRCDepositRecordResult(m)
    case m: QueryRCWithdrawalRecord => _cQueryRCWithdrawalRecord(m)
    case m: QueryRCWithdrawalRecordResult => _cQueryRCWithdrawalRecordResult(m)
    case m: QueryTransaction => _cQueryTransaction(m)
    case m: QueryTransactionResult => _cQueryTransactionResult(m)
    case m: QueryTransfer => _cQueryTransfer(m)
    case m: QueryTransferResult => _cQueryTransferResult(m)
    case m: RegisterUserFailed => _cRegisterUserFailed(m)
    case m: RegisterUserSucceeded => _cRegisterUserSucceeded(m)
    case m: RemoveRobotDNAFailed => _cRemoveRobotDNAFailed(m)
    case m: RemoveRobotDNASucceeded => _cRemoveRobotDNASucceeded(m)
    case m: RequestACodeQueryFailed => _cRequestACodeQueryFailed(m)
    case m: RequestACodeQuerySucceeded => _cRequestACodeQuerySucceeded(m)
    case m: RequestBCodeRechargeFailed => _cRequestBCodeRechargeFailed(m)
    case m: RequestBCodeRechargeSucceeded => _cRequestBCodeRechargeSucceeded(m)
    case m: RequestConfirmRCFailed => _cRequestConfirmRCFailed(m)
    case m: RequestConfirmRCSucceeded => _cRequestConfirmRCSucceeded(m)
    case m: RequestGenerateABCodeFailed => _cRequestGenerateABCodeFailed(m)
    case m: RequestGenerateABCodeSucceeded => _cRequestGenerateABCodeSucceeded(m)
    case m: RequestPasswordResetFailed => _cRequestPasswordResetFailed(m)
    case m: RequestPasswordResetSucceeded => _cRequestPasswordResetSucceeded(m)
    case m: RequestTransferFailed => _cRequestTransferFailed(m)
    case m: RequestTransferSucceeded => _cRequestTransferSucceeded(m)
    case m: ResetPasswordFailed => _cResetPasswordFailed(m)
    case m: ResetPasswordSucceeded => _cResetPasswordSucceeded(m)
    case m: SetNotification => _cSetNotification(m)
    case m: SubmitOrderFailed => _cSubmitOrderFailed(m)
    case m: TakeSnapshotNow => _cTakeSnapshotNow(m)
    case m: TransferCryptoCurrency => _cTransferCryptoCurrency(m)
    case m: TransferCryptoCurrencyResult => _cTransferCryptoCurrencyResult(m)
    case m: UpdateUserProfileFailed => _cUpdateUserProfileFailed(m)
    case m: UpdateUserProfileSucceeded => _cUpdateUserProfileSucceeded(m)
    case m: ValidatePasswordResetToken => _cValidatePasswordResetToken(m)
    case m: VerifyEmail => _cVerifyEmail(m)
    case m: VerifyEmailFailed => _cVerifyEmailFailed(m)
    case m: VerifyEmailSucceeded => _cVerifyEmailSucceeded(m)
    case m: VerifyGoogleAuthCode => _cVerifyGoogleAuthCode(m)
    case m: TAccountState => _cTAccountState(m)
    case m: TAccountTransferState => _cTAccountTransferState(m)
    case m: TApiSecretState => _cTApiSecretState(m)
    case m: TAssetState => _cTAssetState(m)
    case m: TBitwayState => _cTBitwayState(m)
    case m: TCandleDataState => _cTCandleDataState(m)
    case m: TMarketState => _cTMarketState(m)
    case m: TMetricsState => _cTMetricsState(m)
    case m: TRobotState => _cTRobotState(m)
    case m: TSimpleState => _cTSimpleState(m)
    case m: TUserState => _cTUserState(m)

    case m => throw new IllegalArgumentException("Cannot serialize object: " + m)
  }

  def fromBinary(bytes: Array[Byte],
    clazz: Option[Class[_]]): AnyRef = clazz match {
    case Some(c) if c == classOf[ABCodeItem.Immutable] => _cABCodeItem.invert(bytes).get
    case Some(c) if c == classOf[AccountTransfer.Immutable] => _cAccountTransfer.invert(bytes).get
    case Some(c) if c == classOf[AddressStatusResult.Immutable] => _cAddressStatusResult.invert(bytes).get
    case Some(c) if c == classOf[ApiSecret.Immutable] => _cApiSecret.invert(bytes).get
    case Some(c) if c == classOf[BlockIndex.Immutable] => _cBlockIndex.invert(bytes).get
    case Some(c) if c == classOf[CandleData.Immutable] => _cCandleData.invert(bytes).get
    case Some(c) if c == classOf[CandleDataItem.Immutable] => _cCandleDataItem.invert(bytes).get
    case Some(c) if c == classOf[CashAccount.Immutable] => _cCashAccount.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyBlock.Immutable] => _cCryptoCurrencyBlock.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyNetworkStatus.Immutable] => _cCryptoCurrencyNetworkStatus.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyTransaction.Immutable] => _cCryptoCurrencyTransaction.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyTransactionPort.Immutable] => _cCryptoCurrencyTransactionPort.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyTransferInfo.Immutable] => _cCryptoCurrencyTransferInfo.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyTransferItem.Immutable] => _cCryptoCurrencyTransferItem.invert(bytes).get
    case Some(c) if c == classOf[CurrentAsset.Immutable] => _cCurrentAsset.invert(bytes).get
    case Some(c) if c == classOf[CurrentPrice.Immutable] => _cCurrentPrice.invert(bytes).get
    case Some(c) if c == classOf[Cursor.Immutable] => _cCursor.invert(bytes).get
    case Some(c) if c == classOf[ExportOpenDataMap.Immutable] => _cExportOpenDataMap.invert(bytes).get
    case Some(c) if c == classOf[Fee.Immutable] => _cFee.invert(bytes).get
    case Some(c) if c == classOf[HistoryAsset.Immutable] => _cHistoryAsset.invert(bytes).get
    case Some(c) if c == classOf[HistoryPrice.Immutable] => _cHistoryPrice.invert(bytes).get
    case Some(c) if c == classOf[MarketDepth.Immutable] => _cMarketDepth.invert(bytes).get
    case Some(c) if c == classOf[MarketDepthItem.Immutable] => _cMarketDepthItem.invert(bytes).get
    case Some(c) if c == classOf[MarketEvent.Immutable] => _cMarketEvent.invert(bytes).get
    case Some(c) if c == classOf[MarketSide.Immutable] => _cMarketSide.invert(bytes).get
    case Some(c) if c == classOf[Metrics.Immutable] => _cMetrics.invert(bytes).get
    case Some(c) if c == classOf[MetricsByMarket.Immutable] => _cMetricsByMarket.invert(bytes).get
    case Some(c) if c == classOf[Notification.Immutable] => _cNotification.invert(bytes).get
    case Some(c) if c == classOf[Order.Immutable] => _cOrder.invert(bytes).get
    case Some(c) if c == classOf[OrderInfo.Immutable] => _cOrderInfo.invert(bytes).get
    case Some(c) if c == classOf[OrderUpdate.Immutable] => _cOrderUpdate.invert(bytes).get
    case Some(c) if c == classOf[QueryMarketSide.Immutable] => _cQueryMarketSide.invert(bytes).get
    case Some(c) if c == classOf[RDouble.Immutable] => _cRDouble.invert(bytes).get
    case Some(c) if c == classOf[RedeliverFilterData.Immutable] => _cRedeliverFilterData.invert(bytes).get
    case Some(c) if c == classOf[RedeliverFilters.Immutable] => _cRedeliverFilters.invert(bytes).get
    case Some(c) if c == classOf[Refund.Immutable] => _cRefund.invert(bytes).get
    case Some(c) if c == classOf[SpanCursor.Immutable] => _cSpanCursor.invert(bytes).get
    case Some(c) if c == classOf[TAddressStatus.Immutable] => _cTAddressStatus.invert(bytes).get
    case Some(c) if c == classOf[TMetricsObserver.Immutable] => _cTMetricsObserver.invert(bytes).get
    case Some(c) if c == classOf[TRobot.Immutable] => _cTRobot.invert(bytes).get
    case Some(c) if c == classOf[TStackQueue.Immutable] => _cTStackQueue.invert(bytes).get
    case Some(c) if c == classOf[TWindowQueue.Immutable] => _cTWindowQueue.invert(bytes).get
    case Some(c) if c == classOf[TWindowVector.Immutable] => _cTWindowVector.invert(bytes).get
    case Some(c) if c == classOf[Transaction.Immutable] => _cTransaction.invert(bytes).get
    case Some(c) if c == classOf[UserAccount.Immutable] => _cUserAccount.invert(bytes).get
    case Some(c) if c == classOf[UserLogsState.Immutable] => _cUserLogsState.invert(bytes).get
    case Some(c) if c == classOf[UserProfile.Immutable] => _cUserProfile.invert(bytes).get
    case Some(c) if c == classOf[AddRobotDNAFailed.Immutable] => _cAddRobotDNAFailed.invert(bytes).get
    case Some(c) if c == classOf[AddRobotDNASucceeded.Immutable] => _cAddRobotDNASucceeded.invert(bytes).get
    case Some(c) if c == classOf[AdminCommandResult.Immutable] => _cAdminCommandResult.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmTransferFailure.Immutable] => _cAdminConfirmTransferFailure.invert(bytes).get
    case Some(c) if c == classOf[AdminConfirmTransferSuccess.Immutable] => _cAdminConfirmTransferSuccess.invert(bytes).get
    case Some(c) if c == classOf[AllocateNewAddress.Immutable] => _cAllocateNewAddress.invert(bytes).get
    case Some(c) if c == classOf[AllocateNewAddressResult.Immutable] => _cAllocateNewAddressResult.invert(bytes).get
    case Some(c) if c == classOf[ApiSecretOperationResult.Immutable] => _cApiSecretOperationResult.invert(bytes).get
    case Some(c) if c == classOf[BitwayMessage.Immutable] => _cBitwayMessage.invert(bytes).get
    case Some(c) if c == classOf[BitwayRequest.Immutable] => _cBitwayRequest.invert(bytes).get
    case Some(c) if c == classOf[CancelOrderFailed.Immutable] => _cCancelOrderFailed.invert(bytes).get
    case Some(c) if c == classOf[CryptoCurrencyBlocksMessage.Immutable] => _cCryptoCurrencyBlocksMessage.invert(bytes).get
    case Some(c) if c == classOf[CryptoTransferFailed.Immutable] => _cCryptoTransferFailed.invert(bytes).get
    case Some(c) if c == classOf[CryptoTransferSucceeded.Immutable] => _cCryptoTransferSucceeded.invert(bytes).get
    case Some(c) if c == classOf[DoAddNewApiSecret.Immutable] => _cDoAddNewApiSecret.invert(bytes).get
    case Some(c) if c == classOf[DoAddRobotDNA.Immutable] => _cDoAddRobotDNA.invert(bytes).get
    case Some(c) if c == classOf[DoCancelOrder.Immutable] => _cDoCancelOrder.invert(bytes).get
    case Some(c) if c == classOf[DoCancelTransfer.Immutable] => _cDoCancelTransfer.invert(bytes).get
    case Some(c) if c == classOf[DoDeleteApiSecret.Immutable] => _cDoDeleteApiSecret.invert(bytes).get
    case Some(c) if c == classOf[DoRegisterUser.Immutable] => _cDoRegisterUser.invert(bytes).get
    case Some(c) if c == classOf[DoRemoveRobotDNA.Immutable] => _cDoRemoveRobotDNA.invert(bytes).get
    case Some(c) if c == classOf[DoRequestACodeQuery.Immutable] => _cDoRequestACodeQuery.invert(bytes).get
    case Some(c) if c == classOf[DoRequestBCodeRecharge.Immutable] => _cDoRequestBCodeRecharge.invert(bytes).get
    case Some(c) if c == classOf[DoRequestConfirmRC.Immutable] => _cDoRequestConfirmRC.invert(bytes).get
    case Some(c) if c == classOf[DoRequestGenerateABCode.Immutable] => _cDoRequestGenerateABCode.invert(bytes).get
    case Some(c) if c == classOf[DoRequestPasswordReset.Immutable] => _cDoRequestPasswordReset.invert(bytes).get
    case Some(c) if c == classOf[DoRequestTransfer.Immutable] => _cDoRequestTransfer.invert(bytes).get
    case Some(c) if c == classOf[DoResetPassword.Immutable] => _cDoResetPassword.invert(bytes).get
    case Some(c) if c == classOf[DoSendEmail.Immutable] => _cDoSendEmail.invert(bytes).get
    case Some(c) if c == classOf[DoSimulateOrderSubmission.Immutable] => _cDoSimulateOrderSubmission.invert(bytes).get
    case Some(c) if c == classOf[DoSubmitOrder.Immutable] => _cDoSubmitOrder.invert(bytes).get
    case Some(c) if c == classOf[DoUpdateMetrics.Immutable] => _cDoUpdateMetrics.invert(bytes).get
    case Some(c) if c == classOf[DoUpdateUserProfile.Immutable] => _cDoUpdateUserProfile.invert(bytes).get
    case Some(c) if c == classOf[DumpStateToFile.Immutable] => _cDumpStateToFile.invert(bytes).get
    case Some(c) if c == classOf[GenerateAddresses.Immutable] => _cGenerateAddresses.invert(bytes).get
    case Some(c) if c == classOf[GenerateAddressesResult.Immutable] => _cGenerateAddressesResult.invert(bytes).get
    case Some(c) if c == classOf[GetMissedCryptoCurrencyBlocks.Immutable] => _cGetMissedCryptoCurrencyBlocks.invert(bytes).get
    case Some(c) if c == classOf[GoogleAuthCodeVerificationResult.Immutable] => _cGoogleAuthCodeVerificationResult.invert(bytes).get
    case Some(c) if c == classOf[Login.Immutable] => _cLogin.invert(bytes).get
    case Some(c) if c == classOf[LoginFailed.Immutable] => _cLoginFailed.invert(bytes).get
    case Some(c) if c == classOf[LoginSucceeded.Immutable] => _cLoginSucceeded.invert(bytes).get
    case Some(c) if c == classOf[MessageNotSupported.Immutable] => _cMessageNotSupported.invert(bytes).get
    case Some(c) if c == classOf[MultiCryptoCurrencyTransactionMessage.Immutable] => _cMultiCryptoCurrencyTransactionMessage.invert(bytes).get
    case Some(c) if c == classOf[OrderCancelled.Immutable] => _cOrderCancelled.invert(bytes).get
    case Some(c) if c == classOf[OrderFundFrozen.Immutable] => _cOrderFundFrozen.invert(bytes).get
    case Some(c) if c == classOf[OrderSubmissionSimulated.Immutable] => _cOrderSubmissionSimulated.invert(bytes).get
    case Some(c) if c == classOf[OrderSubmitted.Immutable] => _cOrderSubmitted.invert(bytes).get
    case Some(c) if c == classOf[PasswordResetTokenValidationResult.Immutable] => _cPasswordResetTokenValidationResult.invert(bytes).get
    case Some(c) if c == classOf[QueryAccount.Immutable] => _cQueryAccount.invert(bytes).get
    case Some(c) if c == classOf[QueryAccountResult.Immutable] => _cQueryAccountResult.invert(bytes).get
    case Some(c) if c == classOf[QueryAccountStatistics.Immutable] => _cQueryAccountStatistics.invert(bytes).get
    case Some(c) if c == classOf[QueryAccountStatisticsResult.Immutable] => _cQueryAccountStatisticsResult.invert(bytes).get
    case Some(c) if c == classOf[QueryApiSecrets.Immutable] => _cQueryApiSecrets.invert(bytes).get
    case Some(c) if c == classOf[QueryApiSecretsResult.Immutable] => _cQueryApiSecretsResult.invert(bytes).get
    case Some(c) if c == classOf[QueryAsset.Immutable] => _cQueryAsset.invert(bytes).get
    case Some(c) if c == classOf[QueryAssetResult.Immutable] => _cQueryAssetResult.invert(bytes).get
    case Some(c) if c == classOf[QueryCandleData.Immutable] => _cQueryCandleData.invert(bytes).get
    case Some(c) if c == classOf[QueryCandleDataResult.Immutable] => _cQueryCandleDataResult.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyAddressStatus.Immutable] => _cQueryCryptoCurrencyAddressStatus.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyAddressStatusResult.Immutable] => _cQueryCryptoCurrencyAddressStatusResult.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyNetworkStatus.Immutable] => _cQueryCryptoCurrencyNetworkStatus.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyNetworkStatusResult.Immutable] => _cQueryCryptoCurrencyNetworkStatusResult.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyTransfer.Immutable] => _cQueryCryptoCurrencyTransfer.invert(bytes).get
    case Some(c) if c == classOf[QueryCryptoCurrencyTransferResult.Immutable] => _cQueryCryptoCurrencyTransferResult.invert(bytes).get
    case Some(c) if c == classOf[QueryMarketDepth.Immutable] => _cQueryMarketDepth.invert(bytes).get
    case Some(c) if c == classOf[QueryMarketDepthResult.Immutable] => _cQueryMarketDepthResult.invert(bytes).get
    case Some(c) if c == classOf[QueryNotification.Immutable] => _cQueryNotification.invert(bytes).get
    case Some(c) if c == classOf[QueryNotificationResult.Immutable] => _cQueryNotificationResult.invert(bytes).get
    case Some(c) if c == classOf[QueryOrder.Immutable] => _cQueryOrder.invert(bytes).get
    case Some(c) if c == classOf[QueryOrderResult.Immutable] => _cQueryOrderResult.invert(bytes).get
    case Some(c) if c == classOf[QueryProfile.Immutable] => _cQueryProfile.invert(bytes).get
    case Some(c) if c == classOf[QueryProfileResult.Immutable] => _cQueryProfileResult.invert(bytes).get
    case Some(c) if c == classOf[QueryRCDepositRecord.Immutable] => _cQueryRCDepositRecord.invert(bytes).get
    case Some(c) if c == classOf[QueryRCDepositRecordResult.Immutable] => _cQueryRCDepositRecordResult.invert(bytes).get
    case Some(c) if c == classOf[QueryRCWithdrawalRecord.Immutable] => _cQueryRCWithdrawalRecord.invert(bytes).get
    case Some(c) if c == classOf[QueryRCWithdrawalRecordResult.Immutable] => _cQueryRCWithdrawalRecordResult.invert(bytes).get
    case Some(c) if c == classOf[QueryTransaction.Immutable] => _cQueryTransaction.invert(bytes).get
    case Some(c) if c == classOf[QueryTransactionResult.Immutable] => _cQueryTransactionResult.invert(bytes).get
    case Some(c) if c == classOf[QueryTransfer.Immutable] => _cQueryTransfer.invert(bytes).get
    case Some(c) if c == classOf[QueryTransferResult.Immutable] => _cQueryTransferResult.invert(bytes).get
    case Some(c) if c == classOf[RegisterUserFailed.Immutable] => _cRegisterUserFailed.invert(bytes).get
    case Some(c) if c == classOf[RegisterUserSucceeded.Immutable] => _cRegisterUserSucceeded.invert(bytes).get
    case Some(c) if c == classOf[RemoveRobotDNAFailed.Immutable] => _cRemoveRobotDNAFailed.invert(bytes).get
    case Some(c) if c == classOf[RemoveRobotDNASucceeded.Immutable] => _cRemoveRobotDNASucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestACodeQueryFailed.Immutable] => _cRequestACodeQueryFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestACodeQuerySucceeded.Immutable] => _cRequestACodeQuerySucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestBCodeRechargeFailed.Immutable] => _cRequestBCodeRechargeFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestBCodeRechargeSucceeded.Immutable] => _cRequestBCodeRechargeSucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestConfirmRCFailed.Immutable] => _cRequestConfirmRCFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestConfirmRCSucceeded.Immutable] => _cRequestConfirmRCSucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestGenerateABCodeFailed.Immutable] => _cRequestGenerateABCodeFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestGenerateABCodeSucceeded.Immutable] => _cRequestGenerateABCodeSucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestPasswordResetFailed.Immutable] => _cRequestPasswordResetFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestPasswordResetSucceeded.Immutable] => _cRequestPasswordResetSucceeded.invert(bytes).get
    case Some(c) if c == classOf[RequestTransferFailed.Immutable] => _cRequestTransferFailed.invert(bytes).get
    case Some(c) if c == classOf[RequestTransferSucceeded.Immutable] => _cRequestTransferSucceeded.invert(bytes).get
    case Some(c) if c == classOf[ResetPasswordFailed.Immutable] => _cResetPasswordFailed.invert(bytes).get
    case Some(c) if c == classOf[ResetPasswordSucceeded.Immutable] => _cResetPasswordSucceeded.invert(bytes).get
    case Some(c) if c == classOf[SetNotification.Immutable] => _cSetNotification.invert(bytes).get
    case Some(c) if c == classOf[SubmitOrderFailed.Immutable] => _cSubmitOrderFailed.invert(bytes).get
    case Some(c) if c == classOf[TakeSnapshotNow.Immutable] => _cTakeSnapshotNow.invert(bytes).get
    case Some(c) if c == classOf[TransferCryptoCurrency.Immutable] => _cTransferCryptoCurrency.invert(bytes).get
    case Some(c) if c == classOf[TransferCryptoCurrencyResult.Immutable] => _cTransferCryptoCurrencyResult.invert(bytes).get
    case Some(c) if c == classOf[UpdateUserProfileFailed.Immutable] => _cUpdateUserProfileFailed.invert(bytes).get
    case Some(c) if c == classOf[UpdateUserProfileSucceeded.Immutable] => _cUpdateUserProfileSucceeded.invert(bytes).get
    case Some(c) if c == classOf[ValidatePasswordResetToken.Immutable] => _cValidatePasswordResetToken.invert(bytes).get
    case Some(c) if c == classOf[VerifyEmail.Immutable] => _cVerifyEmail.invert(bytes).get
    case Some(c) if c == classOf[VerifyEmailFailed.Immutable] => _cVerifyEmailFailed.invert(bytes).get
    case Some(c) if c == classOf[VerifyEmailSucceeded.Immutable] => _cVerifyEmailSucceeded.invert(bytes).get
    case Some(c) if c == classOf[VerifyGoogleAuthCode.Immutable] => _cVerifyGoogleAuthCode.invert(bytes).get
    case Some(c) if c == classOf[TAccountState.Immutable] => _cTAccountState.invert(bytes).get
    case Some(c) if c == classOf[TAccountTransferState.Immutable] => _cTAccountTransferState.invert(bytes).get
    case Some(c) if c == classOf[TApiSecretState.Immutable] => _cTApiSecretState.invert(bytes).get
    case Some(c) if c == classOf[TAssetState.Immutable] => _cTAssetState.invert(bytes).get
    case Some(c) if c == classOf[TBitwayState.Immutable] => _cTBitwayState.invert(bytes).get
    case Some(c) if c == classOf[TCandleDataState.Immutable] => _cTCandleDataState.invert(bytes).get
    case Some(c) if c == classOf[TMarketState.Immutable] => _cTMarketState.invert(bytes).get
    case Some(c) if c == classOf[TMetricsState.Immutable] => _cTMetricsState.invert(bytes).get
    case Some(c) if c == classOf[TRobotState.Immutable] => _cTRobotState.invert(bytes).get
    case Some(c) if c == classOf[TSimpleState.Immutable] => _cTSimpleState.invert(bytes).get
    case Some(c) if c == classOf[TUserState.Immutable] => _cTUserState.invert(bytes).get

    case Some(c) => throw new IllegalArgumentException("Cannot deserialize class: " + c.getCanonicalName)
    case None => throw new IllegalArgumentException("No class found in EventSerializer when deserializing array: " + bytes.mkString("").take(100))
  }
}
