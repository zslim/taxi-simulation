package hu.corvinus.kt.zsofi.taxi.helpers

import hu.corvinus.kt.zsofi.taxi.framework.State
import hu.corvinus.kt.zsofi.taxi.operation.{Company, OrderRecordState}
import org.joda.time.DateTime

object DataHelper {

  def getOrdersOfCompanyForMonth(date: DateTime, company: Company.Value): Array[OrderRecordState] = {
    val orders: Array[OrderRecordState] = State.orderHistory.filter(
      record => TimeHelper.inSameMonth(date, record.dateTime) && record.company == company
    )
    orders
  }

  def collectMonthlyRideNumbers: List[Array[String]] = {
    val monthIterator: List[DateTime] = TimeHelper.getListOfMonths
    var rows: List[Array[String]] = List()
    for (date <- monthIterator) {
      val month: String = s"${date.getYear}-${date.getMonthOfYear}"
      val taxiRides: Int = getOrdersOfCompanyForMonth(date, Company.TAXI).length
      val uberRides: Int = getOrdersOfCompanyForMonth(date, Company.UBER).length
      rows = rows :+ Array(month, taxiRides.toString, uberRides.toString)
    }
    rows
  }

}
