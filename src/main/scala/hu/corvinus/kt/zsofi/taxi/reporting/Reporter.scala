package hu.corvinus.kt.zsofi.taxi.reporting

import hu.corvinus.kt.zsofi.taxi.framework.State._
import hu.corvinus.kt.zsofi.taxi.helpers.{AccountingHelper, TimeHelper}
import hu.corvinus.kt.zsofi.taxi.operation.{Company, CompanyChangeRecord}

object Reporter {
  
  def reportInitialParameters(): Unit = {
    println("--------------------------------------------------")
    println("Initial parameters")
    println(s"Number of drivers: $numberOfDrivers")
    println(s"Working hours generated randomly between $workingHoursMin and $workingHoursMax (inclusive)")
    println(s"Working days in a month: $workingDaysInMonth")
    println(s"Order distances generated randomly between $distanceMin and $distanceMax (inclusive)")
    println(s"Number of orders in an hour generated randomly between $ordersInHourMin and $ordersInHourMax (inclusive)")
    println(s"Pricing: base fare of $baseFare HUF + $distanceBasedFareUnit HUF per distance unit")
    println(s"Fuel cost per distance unit: $fuelUnitCost")
    println(s"Uber fee from every ride: ${(portionOfFareToUber * 100).toInt}%")
    println(s"Monthly fee for taxi company: $taxiMonthlyFee HUF")
    println("--------------------------------------------------")
  }

  def reportEndStatus(): Unit = {
    val numberOfTaxiDrivers: Int = getNumberOfDriversByCompany(Company.TAXI)
    val numberOfUberDrivers: Int = getNumberOfDriversByCompany(Company.UBER)
    val avgWorkingHoursAtTaxi: Double = getAvgWorkingHoursByCompany(Company.TAXI)
    val avgWorkingHoursAtUber: Double = getAvgWorkingHoursByCompany(Company.UBER)
    println("--------------------------------------------------")
    println("End of the year report")
    println(s"Number of TAXI drivers: $numberOfTaxiDrivers")
    println(s"Average working hours of TAXI drivers: $avgWorkingHoursAtTaxi")
    println(s"Number of UBER drivers: $numberOfUberDrivers")
    println(s"Average working hours of UBER drivers: $avgWorkingHoursAtUber")
    println("--------------------------------------------------")
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
//    value of decision variables
    val expectedProfitsTaxi = for (k <- workingHoursMin to workingHoursMax) yield (k, AccountingHelper.getExpectedProfit(k, Company.TAXI))
    val expectedProfitsUber = for (k <- workingHoursMin to workingHoursMax) yield (k, AccountingHelper.getExpectedProfit(k, Company.UBER))
    println("Data supporting decisions:")
    println(s"Expected profits for TAXI: $expectedProfitsTaxi")
    println(s"Expected profits for UBER: $expectedProfitsUber")
//    driver changes
    val changesInLastMonth: Array[CompanyChangeRecord] = changeHistory.filter(r => TimeHelper.inThisMonth(r.dateTime))
    println("Company changes in this month:")
    for (record <- changesInLastMonth) println(record)
//    number of drivers
    val numberOfTaxiDrivers: Int = getNumberOfDriversByCompany(Company.TAXI)
    val numberOfUberDrivers: Int = getNumberOfDriversByCompany(Company.UBER)
    println(s"Number of TAXI drivers: $numberOfTaxiDrivers")
    println(s"Number of UBER drivers: $numberOfUberDrivers")
  }

}
