/**
 * Copyright 2014 Coinport Inc. All Rights Reserved.
 * Author: c@coinport.com (Chao Ma)
 */

var CryptoAgentManager = require('./crypto/crypto_agent_manager').CryptoAgentManager,
    DataTypes          = require('../../../gen-nodejs/data_types'),
    Currency           = DataTypes.Currency;
var program = require('commander');
var btc = {
    currency: Currency.BTC,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '8332',
        },
        minConfirm: 1,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.BTC,
        ip: 'bitway',
        port: '6379',
    }
};

var ltc = {
    currency: Currency.LTC,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '9332',
        },
        minConfirm: 4,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.LTC,
        ip: 'bitway',
        port: '6379',
    }
};

var dog = {
    currency: Currency.DOGE,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '22555',
        },
        minConfirm: 4,
        minerFee: 1,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.DOGE,
        ip: 'bitway',
        port: '6379',
    }
};

var drk = {
    currency: Currency.DRK,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '7332',
        },
        minConfirm: 4,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.DRK,
        ip: 'bitway',
        port: '6379',
    }
};

var bc = {
    currency: Currency.BC,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '15715',
        },
        minConfirm: 10,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.BC,
        ip: 'bitway',
        port: '6379',
    }
};

var vrc = {
    currency: Currency.VRC,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '58683',
        },
        minConfirm: 4,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.VRC,
        ip: 'bitway',
        port: '6379',
    }
};

var zet = {
    currency: Currency.ZET,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'user',
            pass: 'pass',
            host: 'bitway',
            port: '6332',
        },
        minConfirm: 10,
        minerFee: 0.0001,
        checkInterval : 5000,
        walletPassPhrase: ""
    },
    redisProxyConfig: {
        currency: Currency.ZET,
        ip: 'bitway',
        port: '6379',
    }
};

var btsx = {
    currency: Currency.BTSX,
    cryptoConfig: {
        cryptoRpcConfig: {
            protocol: 'http',
            user: 'test',
            pass: 'test',
            host: '127.0.0.1',
            port: 9989,
        },
        hotAccountName: "coinport-deposit",
        minConfirm: 20,
        checkInterval : 5000,
        walletName: "coinport",
        walletPassPhrase: "coinportpassword"
    },
    redisProxyConfig: {
        currency: Currency.BTSX,
        ip: 'bitway',
        port: '6379',
    }
};

var xrp = {
    currency: Currency.XRP,
    cryptoConfig: {
        hotAccount: "",
        checkInterval : 10000,
        secret: "",
        walletPassPhrase: "",
        trustGateway: [
            {
                gatewayName: "ripplefox",
                gateway: "rKiCet8SdvWxPXnAgYarFUXMh1zCPz432Y",
                withdrawFeeRate: 0.003
            }
        ]
    },
    redisProxyConfig: {
        currency: Currency.XRP,
        ip: 'bitway',
        port: '6379',
    }
};

var configs = [ btc, ltc, dog, drk, bc, vrc, zet, btsx];
// var configs = [ btc ];
// var configs = [ dog ];
program.parse(process.argv);
if (program.args.length == 1 && program.args[0] && program.args[0].length > 7) {
    for (var i = 0; i < configs.length; i++) {
        configs[i].cryptoConfig.walletPassPhrase = program.args[0];
    }
} else {
    console.log("Password isn't correct!");
    console.log("node index.js [password]");
}
var manager = new CryptoAgentManager(configs);
manager.start();

var logo = "\n" +
" _    _ _                     \n" +
"| |__(_) |___ __ ____ _ _  _  \n" +
"| '_ \\ |  _\\ V  V / _` | || | \n" +
"|_.__/_|\\__|\\_/\\_/\\__,_|\\_, | \n" +
"                        |__/  \n";
console.log(logo);
