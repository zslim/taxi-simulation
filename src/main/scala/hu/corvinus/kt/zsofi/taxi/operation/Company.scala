package hu.corvinus.kt.zsofi.taxi.operation

import scala.util.Random

object Company extends Enumeration {
  type Company = Value
  val TAXI: Company.Value = Value("0")
  val UBER: Company.Value = Value("1")

  def getRandomCompany(): Company.Value = {
    val key = math.round(Random.nextFloat())
    Company.withName(key.toString)
  }
}
