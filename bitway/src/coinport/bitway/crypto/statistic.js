/**
 *Author: yangli - yangli@coinport.com
 *Last modified: 2015-02-12 20:39
 *Filename: statistic.js
 *Copyright 2015 Coinport Inc. All Rights Reserved.
 */
var fs = require('fs');
var Async = require('async');
var userAmountMap = {};

singleStatistic = function(num, callback) {
    var fileName = 'event' + num + '.json';
    console.log(fileName);
    fs.readFile(fileName, function(error, data){
        if (error) {
            console.log(error);
        } else {
            var jsonData = JSON.parse(data);
            for (var i = 0; i < jsonData.events.length; i++) {
                if (jsonData.events[i].OrderSubmitted
                    && (jsonData.events[i].OrderSubmitted.originOrderInfo.side == 'BTC-CNY'  || jsonData.events[i].OrderSubmitted.originOrderInfo.side == 'CNY-BTC')
                    && jsonData.events[i].OrderSubmitted.originOrderInfo.status == 'FullyExecuted') {
                    var value = 0;
                    if (jsonData.events[i].OrderSubmitted.originOrderInfo.side == 'BTC-CNY') {
                        value = jsonData.events[i].OrderSubmitted.originOrderInfo.outAmount/100000000.0;
                    } else {
                        value = jsonData.events[i].OrderSubmitted.originOrderInfo.inAmount/100000000.0;
                    }
                    if (userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId]) {
                    } else {
                        userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId] = 0;
                    }
                    userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId] += value;
                }

                if (jsonData.events[i].OrderCancelled
                    && (jsonData.events[i].OrderCancelled.side == 'BTC-CNY'  || jsonData.events[i].OrderCancelled.side == 'CNY-BTC')) {
                    var value = 0;
                    if (jsonData.events[i].OrderCancelled.side == 'BTC-CNY') {
                        //value = jsonData.events[i].OrderSubmitted.originOrderInfo.outAmount/100000000.0;
                    } else {
                        if (jsonData.events[i].OrderCancelled.order.inAmount) {
                            value = jsonData.events[i].OrderCancelled.order.inAmount/100000000.0;
                            console.log(jsonData.events[i].OrderCancelled.order.userId + "" + value);
                        } else {
                        }
                    }
                    if (userAmountMap[jsonData.events[i].OrderCancelled.order.userId]) {
                    } else {
                        userAmountMap[jsonData.events[i].OrderCancelled.order.userId] = 0;
                    }
                    userAmountMap[jsonData.events[i].OrderCancelled.order.userId] += value;
                }
            }
            var userAmountArray = [];
            for (var i in userAmountMap) {
                var userAmount = [];
                userAmount.id = i;
                userAmount.amount = userAmountMap[i];
                userAmountArray.push(userAmount);
            }
            userAmountArray.sort(compareuserAmount);
            console.log("");
            console.log(userAmountArray);
        }
    });
}

Async.times(13, singleStatistic, function(error, results) {

});

compareuserAmount = function(userAmountA, userAmountB) {                                   
     if (userAmountA.amount > userAmountB.amount) {                                                                      
         return 1;                                                                                                       
     } else if (userAmountA.amount < userAmountB.amount) {                                                               
         return -1;                                                                                                      
     } else {                                                                                                            
         return 0;                                                                                                       
     }                                                                                                                   
 };  
