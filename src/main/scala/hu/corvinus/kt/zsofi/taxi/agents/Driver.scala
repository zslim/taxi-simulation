package hu.corvinus.kt.zsofi.taxi.agents

import hu.corvinus.kt.zsofi.taxi.framework.Clock
import hu.corvinus.kt.zsofi.taxi.helpers.{AccountingHelper, TimeHelper}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, Order, OrderRecordDriver}
import com.github.nscala_time.time.Imports._

class Driver(val workingHours: Int, val startingHour: Int, var company: Company.Value) {

  var orderHistory: Array[OrderRecordDriver] = Array()

  override def toString: String = s"Driver(works $workingHours hours, starts at $startingHour, done ${orderHistory.length} orders)"

  def isWorking: Boolean = Clock.getCurrentHour - startingHour < workingHours  // is Driver working

  def isFree: Boolean = {  // is Driver free to take a new Order
    if (orderHistory.length > 0) {
      val lastOrder: OrderRecordDriver = orderHistory.maxBy(_.dateTime)
      val timeSinceLastOrder: Duration = TimeHelper.hoursPassedSince(lastOrder.dateTime)
      timeSinceLastOrder > 1.hour
    } else true
  }

  def takeOrder(order: Order): Unit = {
    documentOrder(order)
  }

  private def documentOrder(order: Order): Unit = {
    val cost: Int = AccountingHelper.calculateCostsOfOrder(order, company)
    val record: OrderRecordDriver = OrderRecordDriver(Clock.getCurrentDateTime, order.price, cost)
    orderHistory = orderHistory :+ record
  }

  private def chooseCompany(): Company.Value = {
    val expectedProfitTaxi: Int = AccountingHelper.getExpectedIncome(workingHours, Company.TAXI)
    val expectedProfitUber: Int = AccountingHelper.getExpectedIncome(workingHours, Company.UBER)
    if (expectedProfitTaxi > expectedProfitUber) Company.TAXI else Company.UBER
  }

  def takeMonthlyDecision(): Unit = {
    val chosenCompany: Company.Value = chooseCompany()
    if (chosenCompany == this.company) {
      println(s"$this is pleased with current company $company")
    } else {
      println(s"$this is going to change to $chosenCompany")
      company = chosenCompany
    }
  }

}
