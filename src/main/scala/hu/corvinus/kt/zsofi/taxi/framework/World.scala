package hu.corvinus.kt.zsofi.taxi.framework

import hu.corvinus.kt.zsofi.taxi.agents.Driver
import hu.corvinus.kt.zsofi.taxi.helpers.OrderHelper._
import hu.corvinus.kt.zsofi.taxi.helpers.TimeHelper
import hu.corvinus.kt.zsofi.taxi.operation.{Company, Order, OrderRecordState}

import scala.util.Random

object World {

  def operateForAYear(): Unit = {
    while (!TimeHelper.hasAYearPassed) {
      if (TimeHelper.isEndOfMonth) {
        driversDecide()
      }
      operateForAnHour()
    }
  }

  def driversDecide(): Unit = {
    for (driver <- State.drivers) driver.takeMonthlyDecision()
  }

  def operateForAnHour(): Unit = {
    val orders: Array[Order] = ordersOfCurrentHour()
    assignOrders(orders)
    Clock.nextHour()
  }

  def assignOrders(orders: Array[Order]): Unit = {
    for (order <- orders) {
      val availableDrivers: Array[Driver] = State.drivers.filter(d => d.isWorking && d.isFree)
      val selectedDriver: Driver = Random.shuffle(availableDrivers.toList).head
      selectedDriver.takeOrder(order)
      documentOrder(order, selectedDriver.company)
    }
  }

  def documentOrder(order: Order, company: Company.Value): Unit = {
    val record: OrderRecordState = OrderRecordState(Clock.getCurrentDateTime, order.distance, order.price, company)
    State.orderHistory = State.orderHistory :+ record
  }

}

object Initializer {

  def initializeDrivers(): Unit = {
    for (i <- 1 to State.numberOfDrivers) {
      val workingHours: Int = 1 + Random.nextInt(12)
      val startingHour: Int = Random.nextInt(24)  // TODO: this shouldn't be totally random
      val company: Company.Value = Company.getRandomCompany()
      val newDriver: Driver = new Driver(workingHours, startingHour, company)
      State.drivers = State.drivers :+ newDriver
    }
  }
}
