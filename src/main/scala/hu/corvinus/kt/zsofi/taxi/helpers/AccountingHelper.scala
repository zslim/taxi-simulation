package hu.corvinus.kt.zsofi.taxi.helpers

import hu.corvinus.kt.zsofi.taxi.framework.{Clock, State}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, Order, OrderRecordState}

object AccountingHelper {

  def calculateCostsOfOrder(order: Order, company: Company.Value): Int = {
    calculateCostsOfOrder(OrderRecordState(Clock.getCurrentDateTime, order.distance, order.price, company))  // TODO: I cheat here with the datetime
  }

  def calculateCostsOfOrder(order: OrderRecordState): Int = {
    val fuelCost: Int = order.distance * State.fuelUnitCost
    order.company match {
      case Company.TAXI => fuelCost
      case Company.UBER => fuelCost + math.round(order.price * State.portionOfFareToUber).toInt
    }
  }

  def calculateMonthlyCost(company: Company.Value): Int = {
    company match {
      case Company.TAXI => State.taxiMonthlyFee
      case Company.UBER => State.uberMonthlyFee
    }
  }

  def getNetIncomePerOrderForThisMonth(company: Company.Value): Int = {
    val orders: Array[OrderRecordState] = getOrdersOfCompanyForThisMonth(company)
    if (orders.length > 0) {
      math.round((orders.map(_.price).sum - orders.map(calculateCostsOfOrder).sum) / orders.length)
    } else 0
  }

  def getOrdersOfCompanyForThisMonth(company: Company.Value): Array[OrderRecordState] = {
    val orders: Array[OrderRecordState] = State.orderHistory.filter(
      record => TimeHelper.inThisMonth(record.dateTime) && record.company == company
    )
    orders
  }

  def getExpectedProfit(workingHours: Int, company: Company.Value): Int = {
    getNetIncomePerOrderForThisMonth(company) * workingHours * State.workingDaysInMonth - calculateMonthlyCost(company)
  }

}
