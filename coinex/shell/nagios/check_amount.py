#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# Copyright 2014 Coinport.com. All Rights Reserved.
# Author: Wu Xiaolu
# Desp: script checking the coins network is or isn't ok
 
import httplib2
import json
import time

h = httplib2.Http(".cache") 

cny = 0 # change this to your cny amount
gooc = 0 # change this to your gooc amount
count = 0
userId = "0" #change this to your user id

for p in range(1, 100):
    (resp_headers, content) = h.request("https://exchange.coinport.com/api/user/" + userId + "/order/all?limit=50&page=" + str(p), "GET")
    result = json.loads(content)
    items = result['data']['items']
    if (len(items) == 0):
        break;
    for i in items:
        op = i['operation']
        quantity = i['finishedQuantity']['value']
        amount = i['finishedAmount']['value']
        count = count + 1
        if (op == "Buy"):
            cny = cny - amount
            gooc = gooc + quantity * 0.999
        else:
            cny = cny + amount * 0.999
            gooc = gooc - quantity

    print "--------------------------"
    print "cny " + str(cny)
    print "gooc " + str(gooc)
    time.sleep(2)


print "======================================"
print "cny " + str(cny)
print "gooc " + str(gooc)
print "count " + str(count)

