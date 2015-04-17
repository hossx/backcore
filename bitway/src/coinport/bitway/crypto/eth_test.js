/**
 *Author: yangli - yangli@coinport.com
 *Last modified: 2015-03-20 12:58
 *Filename: eth_test.js
 *Copyright 2014 Coinport Inc. All Rights Reserved.
 */

'use strict'

var request = require('request');
var web3 = require('web3');

var basicInfo = {
    method: 'POST',
    url: 'http://localhost:8545',
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json',
    }
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

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getBlockByNumber",                                                              
   "params": [                                                                    
       '0x0',
       true
    ],
   "id":1
};      

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_blockNumber",                                                              
   "params": [                                                                    
    ],
   "id":64
};      

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
       'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'latest'
    ],
   "id":64
};      

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_sendTransaction",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'to': 'd68e0fd3dd478e895223378dae6b19f09dd0ce7f',
       'value': "0x9184e72a"
   }],
   "id":64
};      

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_getTransactionByHash",                                                              
   "params": [
       "0xb3035b4c3e927ae6a6812e2346fb6d4647f48748c8ed5f482c9d1a6ce9e195c5",
   ],
   "id":64
};      

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_sendTransaction",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'data': '606280600c6000396000f3006000357c01000000000000000000000000000000000000000000000000000000009004806384db1c1914602e57005b603a6004356024356044565b8060005260206000f35b600060208363ffffffff16118060575750815b905080505b9291505056'
   }],
   "id":64
};

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_call",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'to': '0x0055d82827cf40565e9415b1643d44782b95cdbe',
       "value":"0x7f110", // 520464
       "gas": "0x7f110", // 520464
       "gasPrice":"0x09184e72a000",
       'data': '0xf718375000000000000000000000000000000000000000000000000000000000000000450000000000000000000000000000000000000000000000000000000000000000'
       },
       "latest" ],
   "id":64
};

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_sendTransaction",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'data': '606280600c6000396000f3006000357c010000000000000000000000000000000000000000000000000000000090048063cdcd77c014602e57005b603a6004356024356044565b8060005260206000f35b600060208363ffffffff16118060575750815b905080505b9291505056'
   }],
   "id":64
};

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_call",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'to': '0x7abeeaad2b70d60cbb14f70d24662d5ca627b11c',
       "value":"0x7f110", // 520464
       "gas": "0x7f110", // 520464
       "gasPrice":"0x09184e72a000",
       'data': '0xf718375000000000000000000000000000000000000000000000000000000000000000450000000000000000000000000000000000000000000000000000000000000000'
       },
       "latest" ],
   "id":64
};

var requestBody = {
   "jsonrpc":"2.0",
   "method": "eth_sendTransaction",                                                              
   "params": [{
       'from': 'b5deb39ddb92d437cc83fab49bb0a5c18c60e33c',
       'to':   '0x76b01dbf75111e85eba70b17cd1abde02885563d',
       'value': "0x72a",
       'data':  "1000001423"
   }],
   "id":64
};      

rpcRequest(basicInfo, requestBody, function(error, result) {
    var response = JSON.parse(result);
    console.log("error %j", error);
    console.log('response', response);
    //var value = web3.toDecimal(response.result.value);
    //console.log(value); // true
});

var rpcRequest = function (basicInfo, requestBody, callback) {
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
            var responseBody = JSON.parse(body);
            console.log("response body: ", responseBody);
            callback(null, body);
        } else {
            console.error("rpcRequest error: ", error);
            callback("error", null);
        }
    });
};
