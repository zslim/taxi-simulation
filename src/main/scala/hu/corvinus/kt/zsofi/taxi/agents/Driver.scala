package hu.corvinus.kt.zsofi.taxi.agents

import hu.corvinus.kt.zsofi.taxi.framework.{Clock, State}
import hu.corvinus.kt.zsofi.taxi.helpers.{AccountingHelper, TimeHelper}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, CompanyChangeRecord, Order, OrderRecordDriver}
import com.github.nscala_time.time.Imports._

class Driver(val id: Int, val workingHours: Int, val startingHour: Int, var company: Company.Value) {

  var orderHistory: Array[OrderRecordDriver] = Array()

  override def toString: String = s"Driver(id: $id, works $workingHours hours, starts at $startingHour, done ${orderHistory.length} orders)"

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
    val expectedProfitTaxi: Int = AccountingHelper.getExpectedProfit(workingHours, Company.TAXI)
    val expectedProfitUber: Int = AccountingHelper.getExpectedProfit(workingHours, Company.UBER)
    if (expectedProfitTaxi > expectedProfitUber) Company.TAXI else Company.UBER
  }

  def takeMonthlyDecision(): Unit = {
    val chosenCompany: Company.Value = chooseCompany()
    if (chosenCompany == this.company) {
    } else {
      company = chosenCompany
      documentChange(chosenCompany)
    }
  }

  def documentChange(target: Company.Value): Unit = {
    val record: CompanyChangeRecord = CompanyChangeRecord(Clock.getCurrentDateTime, id, workingHours, target)
    State.changeHistory = State.changeHistory :+ record
  }

}
