package hu.corvinus.kt.zsofi.taxi.operation

import com.github.nscala_time.time.Imports._

case class OrderRecordDriver(dateTime: DateTime, income: Int, cost: Int)

case class OrderRecordState(dateTime: DateTime, distance: Int, price: Int, company: Company.Value)
