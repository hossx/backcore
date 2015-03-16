/**
 *Author: yangli - yangli@coinport.com
 *Last modified: 2014-06-12 20:39
 *Filename: monitorColdWallet.js
 *Copyright 2014 Coinport Inc. All Rights Reserved.
 */
var fs = require('fs');

var fileName = 'event1.json';
console.log(fileName);
var userAmountMap = {};
fs.readFile(fileName, function(error, data){
    if (error) {
        console.log(error);
    } else {
        var jsonData = JSON.parse(data);
        console.log("%j", jsonData);
        for (var i = 0; i < jsonData.events.length; i++) {
            if (jsonData.events[i].OrderSubmitted && jsonData.events[i].OrderSubmitted.originOrderInfo.side == 'BTC-BTSX' && jsonData.events[i].OrderSubmitted.originOrderInfo.status == 'FullyExecuted') {
                console.log('order: ', jsonData.events[i].OrderSubmitted.originOrderInfo);
                console.log('');
                if (userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId]) {
                userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId] += jsonData.events[i].OrderSubmitted.originOrderInfo.order.quantity;
                console.log(jsonData.events[i].OrderSubmitted.originOrderInfo.order.quantity);} else {
                    userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId] = 0;
                    userAmountMap[jsonData.events[i].OrderSubmitted.originOrderInfo.order.userId] += jsonData.events[i].OrderSubmitted.originOrderInfo.order.quantity;
                }
            }
        }
        console.log('userAmountMap: ', userAmountMap);
        var userAmountArray = [];
        for (var i in userAmountMap) {
            var userAmount = [];
            userAmount.id = i;
            userAmount.amount = userAmountMap[i];
            userAmountArray.push(userAmount);
        }
        userAmountArray.sort(compareuserAmount);
        console.log(userAmountArray);
    }
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
