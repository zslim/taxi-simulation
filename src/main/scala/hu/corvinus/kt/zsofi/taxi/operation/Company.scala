package hu.corvinus.kt.zsofi.taxi.operation

import scala.util.Random

object Company extends Enumeration {
  type Company = Value
  val TAXI: Company.Value = Value
  val UBER: Company.Value = Value

  def getRandomCompany(): Company.Value = {
    val key = math.round(Random.nextFloat())
    key match {
      case 0 => Company.TAXI
      case 1 => Company.UBER
    }
  }
}
