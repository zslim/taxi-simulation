package hu.corvinus.kt.zsofi.taxi.helpers

import hu.corvinus.kt.zsofi.taxi.framework.{Clock, State}
import hu.corvinus.kt.zsofi.taxi.operation.Order

object OrderHelper {

  def ordersOfCurrentHour(): Array[Order] = {
    val hour: Int = Clock.getCurrentHour
    val numberOfOrders: Int = State.getNumberOfOrdersForHour(hour)
    var result: Array[Order] = Array()
    for (_ <- 1 to numberOfOrders) {
      result = result :+ generateOrder()
    }
    result
  }

  def generateOrder(): Order = {
    val distance = Util.getRandomIntBetween(State.distanceMin, State.distanceMax)
    val price = State.getPrice(distance)
    Order(distance, price)
  }

}
