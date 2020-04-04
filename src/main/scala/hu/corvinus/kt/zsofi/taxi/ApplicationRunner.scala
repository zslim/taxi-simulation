package hu.corvinus.kt.zsofi.taxi

import hu.corvinus.kt.zsofi.taxi.framework.World
import hu.corvinus.kt.zsofi.taxi.reporting.Reporter
import com.typesafe.scalalogging.Logger

object ApplicationRunner {
  
  private val logger = Logger("main")

  def main(args: Array[String]): Unit = {
    logger.info("Starting a one-year trial.")
    Reporter.reportInitialParameters()
    World.initializeDrivers()
    World.operateForAYear()
    Reporter.reportEndStatus()
    logger.info("One-year trial is over.")
  }

}
