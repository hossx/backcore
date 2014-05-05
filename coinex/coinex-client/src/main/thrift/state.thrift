/**
 * Copyright {C} 2014 Coinport Inc. <http://www.coinport.com>
 *
 * WARNING:
 *  All structs must have at least 1 parameters, otherwise AKKA serialization fails.
 */

namespace java com.coinport.coinex.data

include "data.thrift"

///////////////////////////////////////////////////////////////////////
///////////////////// PROCESSOR AND VIEW STATES ///////////////////////
///////////////////////////////////////////////////////////////////////

typedef data.Order                 Order
typedef data.MarketSide            MarketSide
typedef data.ApiSecret             ApiSecret
typedef data.UserAccount           UserAccount
typedef data.UserProfile           UserProfile
typedef data.RedeliverFilters      RedeliverFilters
typedef data.ChartTimeDimension    ChartTimeDimension
typedef data.CandleDataItem        CandleDataItem
typedef data.TMetricsObserver      TMetricsObserver
typedef data.CashAccount           CashAccount
typedef data.Currency              Currency
typedef data.ABCodeItem            ABCodeItem
typedef data.Metrics               Metrics
typedef data.CurrencyNetwork       CurrencyNetwork
typedef data.TRobot                TRobot

struct TUserState {
    1: map<i64, i64> idMap
    2: map<i64, UserProfile> profileMap
    3: map<string, i64> passwordResetTokenMap
    4: map<string, i64> verificationTokenMap
    5: i64 lastUserId
}

struct TAccountState {
    1: map<i64, UserAccount> userAccountsMap
    2: RedeliverFilters filters
    3: UserAccount aggregation
    4: i64 lastOrderId
    5: map<i64, ABCodeItem> abCodeMap
    6: map<string, i64> codeAIndexMap
    7: map<string, i64> codeBIndexMap
}

struct TMarketState {
    1: map<MarketSide, list<Order>> orderPools
    2: map<i64, Order> orderMap
    3: optional double priceRestriction
    4: RedeliverFilters filters
}

struct TApiSecretState {
    1: map<string, ApiSecret> identifierLookupMap // key is identifier
    2: map<i64, list<ApiSecret>> userSecretMap // key is userId
    3: string seed
}

struct TAccountTransferState {
    1: i64 lastTransferId
    2: RedeliverFilters filters
}

struct TMarketDepthState {
    1: map<double, i64> askMap
    2: map<double, i64> bidMap
}

struct TExportToMongoState {
    1: i64 height
    2: i64 index
    3: string hash
    4: i64 lastSnapshotTimestamp
    5: string version
}

struct TCandleDataState {
    1: map<ChartTimeDimension, map<i64, CandleDataItem>> candleMap
}

struct TAssetState {
    1: map<i64, map<Currency, i64>> currentAssetMap
    2: map<i64, map<i64, map<Currency, i64>>> historyAssetMap
    3: map<MarketSide, double> currentPriceMap
    4: map<MarketSide, map<i64, double>> historyPriceMap
}

struct TSimpleState {
    1: RedeliverFilters filters
}

struct TMetricsState {
    1: map<MarketSide, TMetricsObserver> observers
    2: RedeliverFilters filters
}

struct TRobotState {
    1: set<TRobot> robotPool
    2: map<i64, TRobot> robotMap
    3: Metrics metrics
    4: map<i64, map<string, string>> robotDNAMap
}

struct TBitwayState {
    1: map<Currency, CurrencyNetwork> stats
}
