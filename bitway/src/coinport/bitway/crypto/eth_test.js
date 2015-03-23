/**
 *Author: yangli - yangli@coinport.com
 *Last modified: 2015-03-20 12:58
 *Filename: eth_test.js
 *Copyright 2014 Coinport Inc. All Rights Reserved.
 */

'use strict'

var Async                         = require('async'),
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
    Currency                      = DataTypes.Currency,
    GenerateAddressesResult       = MessageTypes.GenerateAddressesResult,
    TransferStatus                = DataTypes.TransferStatus,
    CryptoCurrencyAddressType     = DataTypes.CryptoCurrencyAddressType,
    TransferType                  = DataTypes.TransferType,
    BlockIndex                    = DataTypes.BlockIndex,
    CryptoAddress                 = DataTypes.CryptoAddress,
    SyncHotAddressesResult        = MessageTypes.SyncHotAddressesResult,
    http                          = require('http');
var request = require('request');

var requestBody = {
   "jsonrpc":"2.0",
   //"method": "eth_coinbase",                                                              
   //"method": "eth_mining",                                                              
   //"method": "eth_gasPrice",                                                              
   //"method": "eth_accounts",                                                              
   "method": "eth_blockNumber",                                                              
   "params": [                                                                    
    ],
   "id":64
};      

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getBalance",                                                              
   "params": [                                                                    
       '954ca60ea53a305611d8c524d394bea3d8a8bc48',
       'latest'
    ],
   "id":64
};      


var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getBlockTransactionCountByNumber",                                                              
   "params": [                                                                    
       '0x0'
    ],
   "id":64
};      


var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getBlockByNumber",                                                              
   "params": [                                                                    
       '0x0',
       true
    ],
   "id":64
};      


var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getBlockByHash",                                                              
   "params": [                                                                    
       '0xfd4af92a79c7fc2fd8bf0d342f2e832e1d4f485c85b9152d2039e03bc604fdca',
       true
    ],
   "id":64
};      


var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getTransactionByBlockHashAndIndex",                                                              
   "params": [                                                                    
       'oxfd4af92a79c7fc2fd8bf0d342f2e832e1d4f485c85b9152d2039e03bc604fdca',
       '0x0'
    ],
   "id":64
};      


var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getWork",                                                              
   "params": [                                                                    
    ],
   "id":64
};      

request({
    method: 'POST',
    url: 'http://localhost:8080',
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(requestBody)
}, function (error, response, body) {
    if (!error && response.statusCode == 200 && body) {
        console.log('Response:', body);
        var responseBody = JSON.parse(body);
        console.log(responseBody);
    } else {
        self.log.error("sign_ error", error);
    }
});
