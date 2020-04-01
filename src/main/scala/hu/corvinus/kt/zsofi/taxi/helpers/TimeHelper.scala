package hu.corvinus.kt.zsofi.taxi.helpers

import hu.corvinus.kt.zsofi.taxi.framework.Clock
import com.github.nscala_time.time.DurationBuilder
import com.github.nscala_time.time.Imports._

object TimeHelper {

  def hoursPassedSince(dateTime: DateTime): DurationBuilder = {
    ((dateTime to Clock.getCurrentDateTime).millis/1000/60/60).toInt.hour
  }

  def hasAYearPassed: Boolean = Clock.startDateTime + 1.year < Clock.getCurrentDateTime

  def isEndOfMonth: Boolean = {
    val now: DateTime = Clock.getCurrentDateTime
    now.getHourOfDay == 23 && now.getMonthOfYear != (now + 1.day).getMonthOfYear
  }

  def getCurrentMonth: String = {
    val monthMap: Map[Int, String] = Map(1 -> "January", 2 -> "February", 3 -> "March", 4 -> "April", 5 -> "May",
      6 -> "June", 7 -> "July", 8 -> "August", 9 -> "September", 10 -> "October", 11 -> "November", 12 -> "December")
    val currentMonthNumber: Int = Clock.getCurrentDateTime.getMonthOfYear
    monthMap(currentMonthNumber)
  }

  def inThisMonth(datetime: DateTime): Boolean = {
    val currentMonth: Int = Clock.getCurrentDateTime.getMonthOfYear
    datetime.getMonthOfYear == currentMonth
  }

  def prettyDate(dateTime: DateTime): String = s"${dateTime.getYear}-${dateTime.getMonthOfYear}-${dateTime.getDayOfMonth}"

}
