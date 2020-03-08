package hu.corvinus.kt.zsofi.taxi.helpers

import hu.corvinus.kt.zsofi.taxi.framework.Clock
import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._

object TimeHelper {

  def hoursPassedSince(dateTime: DateTime): DurationBuilder = {
    ((Clock.getCurrentDateTime to dateTime).millis/1000/60/60).toInt.hour
  }

  def hasAYearPassed: Boolean = Clock.startDateTime + 1.year < Clock.getCurrentDateTime

  def isEndOfMonth: Boolean = {
    val now: DateTime = Clock.getCurrentDateTime
    now.getHourOfDay == 23 && now.getMonthOfYear != (now + 1.day).getMonthOfYear
  }

  def inLastMonth(datetime: DateTime): Boolean = {
    val oneMonthAgo: DateTime = Clock.getCurrentDateTime.minus(1.month)
    oneMonthAgo < datetime
  }

}
