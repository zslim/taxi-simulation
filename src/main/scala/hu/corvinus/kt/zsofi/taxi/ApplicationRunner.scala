package hu.corvinus.kt.zsofi.taxi

import hu.corvinus.kt.zsofi.taxi.framework.World

object ApplicationRunner {

  def main(args: Array[String]): Unit = {
    println("Starting a one-year trial.")
    World.initializeDrivers()
    World.operateForAYear()
    println("One-year trial is over.")
  }

}
