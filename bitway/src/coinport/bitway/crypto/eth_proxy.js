/**
 *Author:  yangli@coinport.com
 *Last modified: 2015-04-08 15:41
 *Filename: eth_proxy.js
 *Copyright 2015 coinport Inc. All Rights Reserved.
 */
'use strict'

var Async                         = require('async'),
    Bitcore                       = require('bitcore'),
    Events                        = require('events'),
    Util                          = require("util"),
    Crypto                        = require('crypto'),
    Redis                         = require('redis'),
    DataTypes                     = require('../../../../gen-nodejs/data_types'),
    MessageTypes                  = require('../../../../gen-nodejs/message_types'),
    BitwayMessage                 = MessageTypes.BitwayMessage,
    CryptoCurrencyBlockMessage    = MessageTypes.CryptoCurrencyBlockMessage,
    BitwayResponseType            = DataTypes.BitwayResponseType,
    ErrorCode                     = DataTypes.ErrorCode,
    GenerateAddressesResult       = MessageTypes.GenerateAddressesResult,
    TransferStatus                = DataTypes.TransferStatus,
    CryptoCurrencyAddressType     = DataTypes.CryptoCurrencyAddressType,
    Logger                        = require('../logger'),
    TransferType                  = DataTypes.TransferType,
    BlockIndex                    = DataTypes.BlockIndex,
    CryptoAddress                 = DataTypes.CryptoAddress,
    SyncHotAddressesResult        = MessageTypes.SyncHotAddressesResult,
    http                          = require('http');
var request = require('request');

/**
 * Handle the crypto currency network event
 * @param {Currency} currency The handled currency type
 * @param {Map{...}} opt_config The config for CryptoProxy
 *     {
 *       cryptoRpc: xxx,
 *       checkInterval: 5000,
 *       redis: xxx,
 *       minConfirm: 1
 *     }
 * @constructor
 * @extends {Events.EventEmitter}
 */
var CryptoProxy = module.exports.CryptoProxy = function(currency, opt_config) {
    Events.EventEmitter.call(this);

    if (opt_config) {
        opt_config.redis != undefined && (this.redis = opt_config.redis);
        opt_config.checkInterval != undefined && (this.checkInterval = opt_config.checkInterval);
        opt_config.minerFee != undefined && (self.minerFee = opt_config.minerFee);
        opt_config.walletName != undefined && (this.walletName = opt_config.walletName);
        opt_config.walletPassPhrase != undefined && (this.walletPassPhrase = opt_config.walletPassPhrase);
    }

    this.rpcUser = opt_config.cryptoRpcConfig.user;
    this.rpcPass = opt_config.cryptoRpcConfig.pass;
    this.httpOptions = {
      host: opt_config.cryptoRpcConfig.host,
      path: '/rpc',
      method: 'POST',
      timeout:10000,
      port: opt_config.cryptoRpcConfig.port,
      agent: this.disableAgent ? false : undefined,                   
    };

    this.currency || (this.currency = currency);
    this.redis || (this.redis = Redis.createClient('6379', '127.0.0.1'));
    this.redis.on('connect'     , this.logFunction('connect'));
    this.redis.on('ready'       , this.logFunction('ready'));
    this.redis.on('reconnecting', this.logFunction('reconnecting'));
    this.redis.on('error'       , this.logFunction('error'));
    this.redis.on('end'         , this.logFunction('end'));

    this.checkInterval || (this.checkInterval = 5000);
    this.minerFee || (this.minerFee = 0.0001);
    this.processedSigids = this.currency + '_processed_sigids';
    this.lastIndex = this.currency + '_last_index';
    this.log = Logger.logger(this.currency.toString());
    this.hotAccountName = opt_config.hotAccountName;
};
Util.inherits(CryptoProxy, Events.EventEmitter);

CryptoProxy.EventType = {
    TX_ARRIVED : 'tx_arrived',
    BLOCK_ARRIVED : 'block_arrived',
    HOT_ADDRESS_GENERATE : 'hot_address_generate'
};

CryptoProxy.prototype.logFunction = function log(type) {
    var self = this;
    return function() {
        self.log.info(type, 'btsx crypto_proxy');
    };
};

var basicInfo = {
    method: 'POST',
    url: 'http://localhost:8080',
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    }
};

CryptoProxy.prototype.rpcRequest_ = function (requestBody, callback) {
    console.log("basicInfo %j", basicInfo);
    console.log("requestBody %j", requestBody);
    request({
        method: basicInfo.method,
        url: basicInfo.url,
        timeout: basicInfo.timeout,
        headers: basicInfo.headers,
        body: JSON.stringify(requestBody)
    }, function (error, response, body) {
        if (!error && response.statusCode == 200 && body) {
            console.log('Response:', body);
            var responseBody = JSON.parse(body);
            console.log(responseBody);
            callback(null, body);
        } else {
            console.error("sign_ error", error);
            callback("error", null);
        }
    });
};

CryptoProxy.prototype.generateUserAddress = function(request, callback) {
    var self = this;
    self.log.info('** Generate User Address Request Received **');
    self.log.info("generateUserAddress req: " + JSON.stringify(request));
    self.log.info("generateUserAddress do nothing");
};

CryptoProxy.prototype.synchronousHotAddr =  function(request, callback) {
    var self = this;
    self.log.info('** Synchronous Hot Addr Request Received **');
    self.log.info("Synchronous Hot Addr Request: " + JSON.stringify(request));
    var shr = new SyncHotAddressesResult({error: ErrorCode.OK, addresses: []});
    var cryptoAddress = new CryptoAddress({address: self.hotAccount, privateKey: self.secret});
    shr.addresses.push(cryptoAddress);
    callback(self.makeNormalResponse_(BitwayResponseType.SYNC_HOT_ADDRESSES, self.currency, shr));
};

CryptoProxy.prototype.syncPrivateKeys =  function(request, callback) { 
    var self = this;
    self.log.info('** Synchronous Addr Request Received **');
    self.log.info("syncPKs req: " + JSON.stringify(request));
    self.log.info("syncPrivateKeys do nothing");
};

CryptoProxy.prototype.sendTransaction_ = function(type, from, to, value) {
    var self = this;
    var params = {
        "from": from, 
        "to": to,
        "value": value
    };
    var requestBody = {jsonrpc: '2.0', id: 2, method: "eth_sendTransaction", params: params};
    self.log.info("sendTransaction_ request: ", requestBody);
    self.rpcRequest_(requestBody, function(error, result) {
        self.log.info("sendTransaction_ result: ", result);
        if (!error && result.result) {
            var cctx = new CryptoCurrencyTransaction({ids: [], status: TransferStatus.CONFIRMING});
            cctx.ids.push(id);
            cctx.txType = type;
            cctx.txid = result.result;
            cctx.sigId = result.result;
            self.log.info("ids: " + id + " sigId: " + cctx.sigId);
            self.redis.set(cctx.sigId, cctx.ids, function(redisError, redisReply){
                if (redisError) {
                    self.log.error("redis sadd error! ids: ", cctx.ids);
                }
            });
            self.emit(CryptoProxy.EventType.TX_ARRIVED,
                self.makeNormalResponse_(BitwayResponseType.TRANSACTION, self.currency, cctx));
        } else {
            self.log.info("error: ", error);
            var response = new CryptoCurrencyTransaction({ids: id, txType: type, 
                status: TransferStatus.FAILED});
            self.emit(CryptoProxy.EventType.TX_ARRIVED,
                self.makeNormalResponse_(BitwayResponseType.TRANSACTION, self.currency, response));
        }
    });
};

CryptoProxy.prototype.makeTransfer_ = function(type, transferInfo) {
    var self = this;
    switch (type) {
        case TransferType.WITHDRAWAL:
        case TransferType.HOT_TO_COLD:
            self.sendTransaction_(type, from, to, value);
            break;
        default:
            this.log.error("Invalid type: " + type);
    }
};

CryptoProxy.prototype.transfer = function(request, callback) {
    var self = this;
    self.log.info('** TransferRequest Received **');
    self.log.info("transfer req: " + JSON.stringify(request));
    var ids = [];
    for (var i = 0; i < request.transferInfos.length; i++) {
        self.makeTransfer_.bind(self)(request.type, request.transferInfos[i]);
    }
};

CryptoProxy.prototype.multi_transfer = function(request, callback) {
    var self = this;
    self.log.info('**Multi Transfer Request Received **');
    self.log.info("multi transfer req: " + JSON.stringify(request));
    var requestAarry = [];
    for (var key in request.transferInfos) {
        var singleRequest = new TransferCryptoCurrency({currency: self.currency, 
            transferInfos: request.transferInfos[key], type: Number(key)});
        requestAarry.push(singleRequest);
    }
    Async.map(requestAarry, self.transfer.bind(self), function(results) {
    });
}

CryptoProxy.prototype.convertAmount_ = function(value) {
    return value/100000;
};


CryptoProxy.prototype.getMissedBlocks = function(request, callback) {
    var self = this;
    self.log.info('** Get Missed Request Received **');
    self.log.info("getMissedBlocks req:" + JSON.stringify(request));
    self.checkMissedRange_.bind(self)(request);
    Async.compose(self.getReorgBlock_.bind(self),
        self.getReorgPosition_.bind(self))(request, function(err, reorgBlock) {
            if (err) {
                self.log.error(err);
                callback(err);
            } else {
                if (reorgBlock.block) {
                    self.redis.set(self.lastIndex, reorgBlock.block.index.height,function(error, replay) {
                        if (!error) {
                        } else {
                            self.log.error(error);
                            callback(error);
                        }
                    });
                }
                callback(null, self.makeNormalResponse_(BitwayResponseType.GET_MISSED_BLOCKS, self.currency, reorgBlock));
            }
        });
};

CryptoProxy.prototype.checkMissedRange_ = function(request) {
    var self = this;
    self.log.warn("Missed start begin position: " + request.startIndexs[0].height);
    self.log.warn("Missed start end position: " + request.startIndexs[request.startIndexs.length - 1].height);
    self.log.warn("Required block position: " + request.endIndex.height);
    self.log.warn("Behind: " + (request.endIndex.height - request.startIndexs[request.startIndexs.length - 1].height));
};

CryptoProxy.prototype.getReorgPosition_ = function(request, callback) {
    var self = this;
    var indexes = request.startIndexs.map(function(element) {return Number(element.height)});
    Async.map(indexes, self.getBlockHash_.bind(self), function(error, hashArray) {
        if (error) {
            self.log.error(error);
            callback(error);
        } else {
            var position = 0;
            var flag = false;
            for (var i = 0; i < request.startIndexs.length; i++) {
               position = i;
               if (request.startIndexs[i].id != hashArray[i]) {
                   flag = true;
                   break;
               }
            }
            if (flag) {
                if (position == 0) {
                    self.log.error("###### fatal forked ###### height: ", indexes[position]);
                    callback(null, -1);
                } else {
                    self.log.error("###### forked ###### height: ", indexes[position] - 1);
                    callback(null, request.startIndexs[position -1]);
                }
            } else {
                callback(null, request.startIndexs[position]);
            }
        }
    });
};

CryptoProxy.prototype.getReorgBlock_ = function(index, callback) {
    var self = this;
    if (index == -1) {
        var gmb = new CryptoCurrencyBlockMessage({reorgIndex: new BlockIndex(null, null)});
        callback(null, gmb);
    } else {
        self.getCCBlockByIndex_.bind(self)(index.height + 1, function(error, block){
            if (error) {
                self.log.error(error);
                callback(error);
            } else {
                var gmb = new CryptoCurrencyBlockMessage({reorgIndex: index, block: block});
                callback(null, gmb);
            }
        });
    }
};

CryptoProxy.prototype.start = function() {
    var self = this;
    self.checkBlockAfterDelay_();
};

CryptoProxy.prototype.checkBlockAfterDelay_ = function(opt_interval) {
    var self = this;
    var interval = self.checkInterval;
    opt_interval != undefined && (interval = opt_interval)
    setTimeout(self.checkBlock_.bind(self), interval);
};

CryptoProxy.prototype.checkBlock_ = function() {
    var self = this;
    self.getNextCCBlock_(function(error, result){
        if (!error) {
            var response = new CryptoCurrencyBlockMessage({block: result});
            self.emit(CryptoProxy.EventType.BLOCK_ARRIVED,
                self.makeNormalResponse_(BitwayResponseType.AUTO_REPORT_BLOCKS, self.currency, response));
            self.checkBlockAfterDelay_(0);
        } else {
            self.checkBlockAfterDelay_();
        }
   });
};

CryptoProxy.prototype.getNextCCBlock_ = function(callback) {
    var self = this;
    Async.compose(self.getNextCCBlockSinceLastIndex_.bind(self), 
        self.getLastIndex_.bind(self))(callback);
};

CryptoProxy.prototype.getLastIndex_ = function(callback) {
    var self = this;
    self.redis.get(self.lastIndex, function(error, index) {
        var numIndex = Number(index);
        if (!error && numIndex) {
            callback(null, numIndex);
        } else {
            callback(null, -1);
        }
    });
};

CryptoProxy.prototype.getNextCCBlockSinceLastIndex_ = function(index, callback) {
    var self = this;
    self.log.info("getNextCCBlockSinceLastIndex_ index: ", index);
    self.getBlockCount_(function(error, count) {
        self.log.info("getNextCCBlockSinceLastIndex_ count: ", count);
        if (error) {
            self.log.error(error);
            callback(error);
        } else if (index == count) {
            self.log.debug('no new block found');
            callback('no new block found');
        } else {
            var nextIndex = (index == -1) ? count : index + 1;
            self.log.info("getNextCCBlockSinceLastIndex_ nextIndex: ", nextIndex);
            self.redis.del(self.getProcessedSigidsByHeight_(nextIndex - 1), function() {});
            self.getCCBlockByIndex_(nextIndex, callback);
        }
    });
};

CryptoProxy.prototype.getBlockCount_ = function(callback) {
    var self = this;
    var requestBody = {jsonrpc: '2.0', id: 1, method: "eth_blockNumber", params: []};
    self.log.info("getBlockCount_ request: ", request);
    self.rpcRequest_(requestBody, function(error, result) {
        self.log.info("getBlockCount_ result: ", result);
        if (error) {
            CryptoProxy.invokeCallback_(error, function() {return error}, callback);
        } else {
            CryptoProxy.invokeCallback_(error, function() {return result.result}, callback);
        }
    });
};

CryptoProxy.prototype.getCCBlockByIndex_ = function(index, callback) {
    var self = this;
    self.log.info("Enter into getCCBlockByIndex_ index: ", index);
    Async.compose(self.completeTransactions_.bind(self),
        self.getBlockByNumber_.bind(self))(index, function(error, block) {
        if (!error) {
            self.redis.set(self.lastIndex, index, function(errorRedis, retRedis) {
                if (!errorRedis) {
                    callback(null, block);
                } else {
                    self.log.error("getCCBlockByIndex_errorRedis: ", errorRedis);
                    callback(errorRedis);
                }
            });
        } else {
            self.log.error("getCCBlockByIndex_ error: ", error);
            callback(error, null);
        }
    });
};

CryptoProxy.prototype.getCCTxByTxHash_ = function(txHash, callback) {
    var self = this;
    

};

CryptoProxy.prototype.completeTransactions_ = function(blockInfo, callback) {
    var self = this;
    Async.map(blockInfo.txs, self.getCCTxByTxHash_.bind(self), function(error, results) {
        if(!error) {
            callback(null, block);
        } else {
            self.log.error("completeTransactions_ error: " + error);
            callback(error);
        }
    });
}

CryptoProxy.prototype.constructCCTXByTxHistory_ = function(txHistory, callback) {
    var self = this;
    var inputs = [];
    self.log.info("txHistory %j: ", txHistory);
    var ledger_entries = txHistory.ledger_entries;
    self.log.info("ledger_entries: ", ledger_entries);

    Async.parallel ([
        function(cb) {self.constructInputs_.bind(self)(ledger_entries, cb)},
        function(cb) {self.constructOutputs_.bind(self)(ledger_entries, cb)}
        ], function(err, results){
        if (!err) {
            results[0][0].amount += self.convertAmount_(txHistory.fee.amount);
            var cctx = new CryptoCurrencyTransaction({sigId: txHistory.trx_id, txid: txHistory.trx_id,
                ids: [], inputs: results[0], outputs: results[1], 
                minerFee: self.convertAmount_(txHistory.fee.amount)});
            self.redis.get(cctx.sigId, function(error, id) {
                if (!error) {
                    if (id) {
                        cctx.ids.push(Number(id));
                    }
                    callback(null, cctx);
                } else {
                    self.log.error("constructCCTXByTxHistory_", error);
                    callback(error, null);
                }
            });
        } else {
            self.log.error("constructCCTXByTxHistory_", error);
            callback(error, null);
        }
    });
};

CryptoProxy.prototype.constructInputs_ = function(ledgerEntries, callback) {
    var self = this;
    var inputAccountNames = [];
    for (var i = 0; i < ledgerEntries.length; i++) {
        inputAccountNames.push(ledgerEntries[i].from_account);
    }
    Async.map(inputAccountNames, self.getAccountByAccountName_.bind(self), function(errors, results) {
        if (!errors) {
            var inputs = [];
            for (var i = 0; i < results.length; i++) {
                var input = new CryptoCurrencyTransactionPort({accountName: results[i].accountName,
                    address: results[i].address,
                    amount: self.convertAmount_(ledgerEntries[i].amount.amount)});
                inputs.push(input);
            }
            callback(null, inputs);
       } else {
           callback(errors, null);
       }
   });
};

CryptoProxy.prototype.constructOutputs_ = function(ledgerEntries, callback) {
    var self = this;
    var outputAccountNames = [];
    for (var i = 0; i < ledgerEntries.length; i++) {
        outputAccountNames.push(ledgerEntries[i].to_account);
    }
    Async.map(outputAccountNames, self.getAccountByAccountName_.bind(self), function(errors, results) {
        if (!errors) {
            var outputs = [];
            for (var i = 0; i < results.length; i++) {
                var output = new CryptoCurrencyTransactionPort({accountName: results[i].accountName,
                    address: results[i].address,
                    amount: self.convertAmount_(ledgerEntries[i].amount.amount)});
                if (output.accountName == self.hotAccountName) {
                    output.memo = ledgerEntries[i].memo;
                }
                outputs.push(output);
            }
            callback(null, outputs);
        } else {
            callback(errors, null);
        }
    });
};

CryptoProxy.prototype.getBlockHash_ = function(height, callback) {
    var self = this;
    self.log.info("Enter into getBlockHash_ height:", height);
    var params = [];
    params.push(height);
    var requestBody = {jsonrpc: '2.0', id: 2, method: "eth_getBlockByNumber", params: params};
    self.log.info("getBlockHash_ request: ", request);
    self.rpcRequest_(requestBody, function(error, result) {
        if(!error) {
            self.log.info("getBlockHash_ result: ", result);
            callback(null, result.result.hash);
        } else {
            self.log.error("getBlockHash_ error: ", error);
            callback(error, null);
        }
    });
};

CryptoProxy.prototype.makeNormalResponse_ = function(type, currency, response) {
    switch (type) {
        case BitwayResponseType.SYNC_HOT_ADDRESSES:
            this.log.info("sync hot addr response");
            return new BitwayMessage({currency: currency, syncHotAddressesResult: response});
        case BitwayResponseType.SYNC_PRIVATE_KEYS:
            this.log.info("sync addr response");
            return new BitwayMessage({currency: currency, syncPrivateKeysResult: response});
        case BitwayResponseType.GENERATE_ADDRESS:
            this.log.info("generate addr response");
            return new BitwayMessage({currency: currency, generateAddressResponse: response});
        case BitwayResponseType.TRANSFER:
        case BitwayResponseType.TRANSACTION:
            this.log.info("transfer response: " + JSON.stringify(response));
            return new BitwayMessage({currency: currency, tx: response});
        case BitwayResponseType.GET_MISSED_BLOCKS:
        case BitwayResponseType.AUTO_REPORT_BLOCKS:
            this.log.info("blocks response: " + JSON.stringify(response));
            return new BitwayMessage({currency: currency, blockMsg: response});
        default:
            this.log.error("Inavalid Type!");
            return null
    }
};

CryptoProxy.prototype.getProcessedSigidsByHeight_ = function(height) {
    return this.processedSigids + '_' + height;
};

CryptoProxy.invokeCallback_ = function(error, resultFun, callback) {
    if (error) {
        callback(error);
    } else {
        callback(null, resultFun());
    }
};
