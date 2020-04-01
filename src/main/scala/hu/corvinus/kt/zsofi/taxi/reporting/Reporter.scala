package hu.corvinus.kt.zsofi.taxi.reporting

import hu.corvinus.kt.zsofi.taxi.framework.State
import hu.corvinus.kt.zsofi.taxi.operation.Company

object Reporter {

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
    State.drivers.count(_.company == company)
  }

  def getAvgWorkingHoursByCompany(company: Company.Value): Double = {
    val sumOfWorkingHours: Int = State.drivers.filter(_.company == company).map(_.workingHours).sum
    if (sumOfWorkingHours > 0) {
      sumOfWorkingHours / getNumberOfDriversByCompany(company)
    } else 0
  }
// TODO: End of month report
}
