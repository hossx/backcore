package com.coinport.coinex.performance

import scala.util.Random
import com.coinport.coinex.Client
import com.coinport.coinex.api.service.AccountService
import com.coinport.coinex.data._
import com.coinport.coinex.data.Currency._
import akka.actor._
import akka.pattern.ask
import akka.actor.ActorSystem
import akka.actor.Props
import akka.cluster.Cluster
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object PerformanceTest {

  implicit val timeout = Timeout(100 seconds)

  def before {
    // register users
    Client.registerTestUsers
    // deposit
    Client.userMap foreach { kv =>
      AccountService.deposit(kv._2, Btc, 1000000 * 1000)
      AccountService.deposit(kv._2, Cny, 1000000000 * 100)
    }
    Thread.sleep(4000)
  }

  def test(cycle: Int, warmup: Long, endCycle: Long) {
    ////////// register user and deposit
    before

    var count = 0L
    var errorCount = 0L
    val btcSide = MarketSide(Btc, Cny)
    val rmbSide = MarketSide(Cny, Btc)

    var startTime: Long = 0L
    var endTime: Long = 0L

    ////////// prepare test data
    var dataList = List.empty[(Long, MarketSide, Double, Double)]
    1 to cycle foreach { _ =>
      val side = List(btcSide, rmbSide)(Random.nextInt(2))
      var quantity = 0d
      var orderPrice = 0d
      if (side == btcSide) {
        quantity = Random.nextDouble() + Random.nextInt(1000)
        orderPrice = 250 + Random.nextInt(100)
      } else {
        quantity = 100000 + Random.nextInt(900000)
        orderPrice = 1.0 / (250 + Random.nextInt(100))
      }
      val userId = Client.userMap.values.toList(Random.nextInt(Client.userMap.size))
      dataList = dataList.::((userId, side, quantity, orderPrice))
    }

    ////////// execute test
    1 to cycle foreach { i =>
      val j = i - 1
      val f = Client.backend ? DoSubmitOrder(
        dataList(j)._2,
        Order(dataList(j)._1, 0, dataList(j)._3.toLong, price = Some(dataList(j)._4), robotId = Some(dataList(j)._1)))

      f onSuccess {
        case m => {
          count += 1
          if (count == warmup) {
            startTime = System.currentTimeMillis
            println("=" * 50)
            println("startTime : " + startTime)
          } else if (count == endCycle) {
            endTime = System.currentTimeMillis
            println("endTime : " + endTime)
            println("execute Time : " + (endTime - startTime))
            println("qps : " + (endCycle - warmup) * 1000.0 / (endTime - startTime))
            println("=" * 50)
          }
        }
      }

      f onFailure {
        case m => {
          errorCount += 1
          println("execute total error count: " + errorCount)
          println("response error: " + m)
          return
        }
      }
    }
  }
}