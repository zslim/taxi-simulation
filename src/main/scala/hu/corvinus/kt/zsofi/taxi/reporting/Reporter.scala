package hu.corvinus.kt.zsofi.taxi.reporting

import hu.corvinus.kt.zsofi.taxi.framework.State._
import hu.corvinus.kt.zsofi.taxi.helpers.{AccountingHelper, TimeHelper}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, CompanyChangeRecord}
import com.typesafe.scalalogging.Logger
import hu.corvinus.kt.zsofi.taxi.framework.Clock
import org.joda.time.DateTime

object Reporter {

  private val logger = Logger("reporter")
  
  def reportInitialParameters(): Unit = {
    logger.info("--------------------------------------------------")
    logger.info("Initial parameters")
    logger.info(s"Number of drivers: $numberOfDrivers")
    logger.info(s"Working hours generated randomly between $workingHoursMin and $workingHoursMax (inclusive)")
    logger.info(s"Working days in a month: $workingDaysInMonth")
    logger.info(s"Order distances generated randomly between $distanceMin and $distanceMax (inclusive)")
    logger.info(s"Number of orders in an hour generated randomly between $ordersInHourMin and $ordersInHourMax (inclusive)")
    logger.info(s"Pricing: base fare of $baseFare HUF + $distanceBasedFareUnit HUF per distance unit")
    logger.info(s"Fuel cost per distance unit: $fuelUnitCost")
    logger.info(s"Uber fee from every ride: ${(portionOfFareToUber * 100).toInt}%")
    logger.info(s"Monthly fee for taxi company: $taxiMonthlyFee HUF")
    logger.info("--------------------------------------------------")
  }

  def reportEndStatus(): Unit = {
    val numberOfTaxiDrivers: Int = getNumberOfDriversByCompany(Company.TAXI)
    val numberOfUberDrivers: Int = getNumberOfDriversByCompany(Company.UBER)
    val avgWorkingHoursAtTaxi: Double = getAvgWorkingHoursByCompany(Company.TAXI)
    val avgWorkingHoursAtUber: Double = getAvgWorkingHoursByCompany(Company.UBER)
    logger.info("--------------------------------------------------")
    logger.info("End report")
    logger.info(s"Number of TAXI drivers: $numberOfTaxiDrivers")
    logger.info(s"Average working hours of TAXI drivers: $avgWorkingHoursAtTaxi")
    logger.info(s"Number of UBER drivers: $numberOfUberDrivers")
    logger.info(s"Average working hours of UBER drivers: $avgWorkingHoursAtUber")
    logger.info("--------------------------------------------------")
  }

  def getNumberOfDriversByCompany(company: Company.Value): Int = {
    drivers.count(_.company == company)
  }

  def getAvgWorkingHoursByCompany(company: Company.Value): Double = {
    val sumOfWorkingHours: Int = drivers.filter(_.company == company).map(_.workingHours).sum
    if (sumOfWorkingHours > 0) {
      sumOfWorkingHours / getNumberOfDriversByCompany(company)
    } else 0
  }

  def reportMonthlyChange(): Unit = {
    val currentDate: DateTime = Clock.getCurrentDateTime
    val currentYear: Int = currentDate.getYear
    val currentMonth: String = TimeHelper.getCurrentMonth
    logger.info(s"________________ Report on monthly changes - $currentMonth $currentYear ________________")
//    value of decision variables
    val expectedProfitsTaxi = for (k <- workingHoursMin to workingHoursMax) yield (k, AccountingHelper.getExpectedProfit(k, Company.TAXI))
    val expectedProfitsUber = for (k <- workingHoursMin to workingHoursMax) yield (k, AccountingHelper.getExpectedProfit(k, Company.UBER))
    logger.info("Data supporting decisions:")
    logger.info(s"Expected profits for TAXI: $expectedProfitsTaxi")
    logger.info(s"Expected profits for UBER: $expectedProfitsUber")
//    driver changes
    val changesInLastMonth: Array[CompanyChangeRecord] = changeHistory.filter(r => TimeHelper.inSameMonth(currentDate, r.dateTime))
    logger.info("Company changes in this month:")
    for (record <- changesInLastMonth) logger.info(record.toString)
//    number of drivers
    val numberOfTaxiDrivers: Int = getNumberOfDriversByCompany(Company.TAXI)
    val numberOfUberDrivers: Int = getNumberOfDriversByCompany(Company.UBER)
    logger.info(s"Number of TAXI drivers: $numberOfTaxiDrivers")
    logger.info(s"Number of UBER drivers: $numberOfUberDrivers")
  }

}
