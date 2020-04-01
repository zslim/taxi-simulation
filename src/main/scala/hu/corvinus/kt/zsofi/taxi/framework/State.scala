package hu.corvinus.kt.zsofi.taxi.framework

import hu.corvinus.kt.zsofi.taxi.agents.Driver
import hu.corvinus.kt.zsofi.taxi.helpers.Util
import hu.corvinus.kt.zsofi.taxi.operation.{CompanyChangeRecord, OrderRecordState}

object State {

  val numberOfDrivers: Int = 60
  var drivers: Array[Driver] = Array()
  val workingHoursMin: Int = 1
  val workingHoursMax: Int = 12

  val workingDaysInMonth: Int = 30

  val distanceMin: Int = 1
  val distanceMax: Int = 10
  val ordersInHourMin: Int = 4
  val ordersInHourMax: Int = 20

  val baseFare: Int = 450
  val distanceBasedFareUnit: Int = 280

  def getPrice(distance: Int): Int = baseFare + distance * distanceBasedFareUnit

  val fuelUnitCost: Int = 20
  val portionOfFareToUber: Double = 0.2
  val uberMonthlyFee: Int = 0
  val taxiMonthlyFee: Int = 100000

  def getNumberOfOrdersForHour(hour: Int): Int = Util.getRandomIntBetween(ordersInHourMin, ordersInHourMax)  // TODO: maybe this shouldn't be totally random

  var orderHistory: Array[OrderRecordState] = Array()
  var changeHistory: Array[CompanyChangeRecord] = Array()

}


