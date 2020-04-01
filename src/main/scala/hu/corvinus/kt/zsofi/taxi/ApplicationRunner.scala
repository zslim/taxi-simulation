package hu.corvinus.kt.zsofi.taxi

import hu.corvinus.kt.zsofi.taxi.framework.World
import hu.corvinus.kt.zsofi.taxi.reporting.Reporter

object ApplicationRunner {

  def main(args: Array[String]): Unit = {
    println("Starting a one-year trial.")
    Reporter.reportInitialParameters()
    World.initializeDrivers()
    World.operateForAYear()
    Reporter.reportEndStatus()
    println("One-year trial is over.")
  }

}
