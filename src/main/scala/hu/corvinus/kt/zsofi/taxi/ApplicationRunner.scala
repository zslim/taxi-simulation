package hu.corvinus.kt.zsofi.taxi

import hu.corvinus.kt.zsofi.taxi.framework.World
import hu.corvinus.kt.zsofi.taxi.reporting.{DataWriter, Reporter}
import com.typesafe.scalalogging.Logger

object ApplicationRunner {
  
  private val logger = Logger("main")

  def main(args: Array[String]): Unit = {
    logger.info("Starting simulation.")
    Reporter.reportInitialParameters()
    World.initializeDrivers()
    World.simulate()
    DataWriter.writeResultData()
    Reporter.reportEndStatus()
    logger.info("Simulation is over.")
  }

}
