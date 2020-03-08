package hu.corvinus.kt.zsofi.taxi.framework

import com.github.nscala_time.time.Imports._

object Clock {

  val startDateTime: DateTime = (new DateTime).withDate(2015, 1, 1).withTimeAtStartOfDay()
  private var currentDateTime: DateTime = startDateTime

  def getCurrentDateTime: DateTime = currentDateTime
  def getCurrentHour: Int = currentDateTime.getHourOfDay

  def nextHour(): Unit = {
    currentDateTime = currentDateTime + 1.hour
  }

}
