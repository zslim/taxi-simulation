package hu.corvinus.kt.zsofi.taxi.helpers

import scala.util.Random

object Util {

  // Including both minimum and maximum
  def getRandomIntBetween(lower: Int, upper: Int): Int = {
    if (lower > upper) throw new IllegalArgumentException(s"lower ($lower) > upper ($upper)")
    lower + Random.nextInt(upper - lower + 1)
  }
}
