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

  def calculateAvgProfitPerOrderInLastMonth(company: Company.Value): Int = {
    val orders: Array[OrderRecordState] = State.orderHistory.filter(
      record => TimeHelper.inLastMonth(record.dateTime) && record.company == company
    )
    val sumOfIncome: Int = orders.map(_.price).sum
    val costList: Array[Int] = for (order <- orders) yield calculateCostsOfOrder(order)
    val sumOfCostsOfOrders: Int = costList.sum
    val averageProfit: Double = (sumOfIncome - sumOfCostsOfOrders - calculateMonthlyCost(company)) / orders.length
    math.round(averageProfit).toInt
  }

}
