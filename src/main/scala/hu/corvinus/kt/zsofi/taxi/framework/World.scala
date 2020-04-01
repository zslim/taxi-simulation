package hu.corvinus.kt.zsofi.taxi.framework

import hu.corvinus.kt.zsofi.taxi.agents.Driver
import hu.corvinus.kt.zsofi.taxi.helpers.OrderHelper._
import hu.corvinus.kt.zsofi.taxi.helpers.{TimeHelper, Util}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, Order, OrderRecordState}
import hu.corvinus.kt.zsofi.taxi.reporting.Reporter

import scala.util.Random

object World {

  def operateForAYear(): Unit = {
    while (!TimeHelper.hasAYearPassed) {
      if (TimeHelper.isEndOfMonth) {
        val currentMonth: String = TimeHelper.getCurrentMonth
        println(s"\nEnd of $currentMonth")
        driversDecide()
        println(s"________________ Report on monthly changes - $currentMonth ________________")
        Reporter.reportMonthlyChange()
      }
      operateForAnHour()
    }
  }

  def driversDecide(): Array[Driver] = {
    val oldState: Array[Driver] = State.drivers.clone
    for (driver <- State.drivers) driver.takeMonthlyDecision()
    oldState
  }

  def operateForAnHour(): Unit = {
    val orders: Array[Order] = ordersOfCurrentHour()
    assignOrders(orders)
    Clock.nextHour()
  }

  def assignOrders(orders: Array[Order]): Unit = {
    for (order <- orders) {
      val availableDrivers: Array[Driver] = State.drivers.filter(d => d.isWorking && d.isFree)
      if (availableDrivers.length > 0) {
        val selectedDriver: Driver = Random.shuffle(availableDrivers.toList).head
        selectedDriver.takeOrder(order)
        documentOrder(order, selectedDriver.company)
      } else println("No available driver at the moment")
    }
  }

  def documentOrder(order: Order, company: Company.Value): Unit = {
    val record: OrderRecordState = OrderRecordState(Clock.getCurrentDateTime, order.distance, order.price, company)
    State.orderHistory = State.orderHistory :+ record
  }

  def initializeDrivers(): Unit = {
    for (i <- 1 to State.numberOfDrivers) {
      val workingHours: Int = Util.getRandomIntBetween(State.workingHoursMin, State.workingHoursMax)
      val startingHour: Int = Random.nextInt(24)  // TODO: this shouldn't be totally random
      val company: Company.Value = Company.getRandomCompany()
      val newDriver: Driver = new Driver(i, workingHours, startingHour, company)
      State.drivers = State.drivers :+ newDriver
    }
  }
}
