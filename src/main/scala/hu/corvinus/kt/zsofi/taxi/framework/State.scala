package hu.corvinus.kt.zsofi.taxi.framework

import hu.corvinus.kt.zsofi.taxi.agents.Driver
import hu.corvinus.kt.zsofi.taxi.operation.OrderRecordState

import scala.util.Random

object State {

  val numberOfDrivers: Int = 60
  var drivers: Array[Driver] = Array()

  private val baseFare: Int = 450
  private val distanceBasedFareUnit: Int = 280

  def getPrice(distance: Int): Int = baseFare + distance * distanceBasedFareUnit

  val fuelUnitCost: Int = 20
  val portionOfFareToUber: Double = 0.2
  val uberMonthlyFee: Int = 0
  val taxiMonthlyFee: Int = 100000

  def getNumberOfOrdersForHour(hour: Int): Int = 4 + Random.nextInt(20)  // TODO: maybe this shouldn't be totally random

  var orderHistory: Array[OrderRecordState] = Array()

}


