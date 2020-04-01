package hu.corvinus.kt.zsofi.taxi.operation

import com.github.nscala_time.time.Imports._
import hu.corvinus.kt.zsofi.taxi.helpers.TimeHelper

case class OrderRecordDriver(dateTime: DateTime, income: Int, cost: Int)

case class OrderRecordState(dateTime: DateTime, distance: Int, price: Int, company: Company.Value)

case class CompanyChangeRecord(dateTime: DateTime, driverId: Int, workingHours: Int, target: Company.Value) {
  override def toString: String = {
    s"CompanyChangeRecord(on ${TimeHelper.prettyDate(dateTime)}, driver $driverId, who works $workingHours hours, changes for $target)"
  }
}
