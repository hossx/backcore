/**
 * Copyright 2014 Coinport Inc. All Rights Reserved.
 * Author: c@coinport.com (Chao Ma)
 */

var CryptoAgent = require('./crypto_agent').CryptoAgent,
    RedisProxy  = require('../redis/redis_proxy').RedisProxy,
    RpcClient   = require('bitcore').RpcClient,
    Redis       = require('redis'),
    CryptoProxy = require('./crypto_proxy').CryptoProxy,
    DataTypes   = require('../../../../gen-nodejs/data_types'),
    Currency    = DataTypes.Currency,
    BtsxCryptoProxy = require('./btsx_proxy').CryptoProxy;
    RippleCryptoProxy = require('./ripple_proxy').CryptoProxy;
    EthCryptoProxy = require('./eth_proxy').CryptoProxy;

var CryptoAgentManager = module.exports.CryptoAgentManager = function(configs) {
    this.agents = [];
    for (var i = 0; i < configs.length; ++i) {
        var config = configs[i];
        var redisConf = config.redisProxyConfig;
        var redisProxy = new RedisProxy(redisConf.currency, redisConf.ip, redisConf.port);

        var cryptoConfig = config.cryptoConfig;
        cryptoConfig.redis = Redis.createClient(redisConf.port, redisConf.ip);
        if (config.currency == Currency.BTSX) {
            console.log("BTSX");
            var cryptoProxy = new BtsxCryptoProxy(config.currency, cryptoConfig);
        } else if (config.currency == Currency.XRP) {
            console.log("XRP");
            var cryptoProxy = new RippleCryptoProxy(config.currency, cryptoConfig);
        } else if (config.currency == Currency.ETH){
            console.log("ETH");
            var cryptoProxy = new EthCryptoProxy(config.currency, cryptoConfig);
        } else {
            console.log(config.currency.toString());
            console.log("%j", cryptoConfig.cryptoRpcConfig);
            cryptoConfig.cryptoRpc = new RpcClient(cryptoConfig.cryptoRpcConfig);
            var cryptoProxy = new CryptoProxy(config.currency, cryptoConfig);
        }
        this.agents.push(new CryptoAgent(cryptoProxy, redisProxy));
    }
};

CryptoAgentManager.prototype.start = function() {
    for (var i = 0; i < this.agents.length; ++i)
        this.agents[i].start();
};
