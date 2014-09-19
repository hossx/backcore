/**
 * Copyright 2014 Coinport Inc. All Rights Reserved.
 * Author: c@coinport.com (Chao Ma)
 */

import com.coinport.coinex.common._
import com.coinport.coinex.data._
import com.coinport.coinex.data.CryptoAddress
import com.coinport.coinex.data.Currency._
import com.coinport.coinex.bitway._
import scala.concurrent.duration._

import Constants._
import Implicits._

BitwayConfigs(Map(
  Btc -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    batchFetchAddressNum = 10,
    maintainedChainLength = 10,
    coldAddresses = List(
      CryptoAddress("morrnAssmf3LP2UkXST7i3UpKcUbBQX4GU",
        message = Some("coinport"),
        signMessage = Some("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"))
    ),
    enableHotColdTransfer = false
  ),
  Ltc -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 20,
    enableHotColdTransfer = false
  ),
  Doge -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 20,
    coldAddresses = List(
 //     CryptoAddress("D8mHXhuo9XFH5VKVWVWa25eCHbAPu3iGyp",
      CryptoAddress("nUmdT61hFz2MWeHdLbGDjDmdVKVRUiuhnu",
        message = Some("coinport"),
        signMessage = Some("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD"))
    ),
    enableHotColdTransfer = false,
    hot2ColdTransferInterval = 60 seconds,
    hot2ColdTransferIntervalLarge = 150 seconds,
    cold2HotTransferInterval = 300 seconds
  ),
  Bc -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 20,
    enableHotColdTransfer = false
  ),
  Drk -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 20,
    enableHotColdTransfer = false
  ),
  Vrc -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 120,
    enableHotColdTransfer = false
  ),
  Zet -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 120,
    enableHotColdTransfer = false
  ),
  Btsx -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 120,
    enableHotColdTransfer = false,
    confirmNum = 3,
    userIdFromMemo = true,
    isDepositHot = true,
    enableFetchAddress = false
  ),
  Nxt -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 120,
    confirmNum = 1,
    enableHotColdTransfer = false
  ),
  Xrp -> BitwayConfig(
    ip = "bitway",
    port = 6379,
    maintainedChainLength = 120,
    enableHotColdTransfer = false,
    confirmNum = 1
  )
))
