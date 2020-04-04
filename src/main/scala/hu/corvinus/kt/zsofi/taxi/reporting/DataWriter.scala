package hu.corvinus.kt.zsofi.taxi.reporting

import java.io.File

import hu.corvinus.kt.zsofi.taxi.helpers.{DataHelper, TimeHelper}
import hu.corvinus.kt.zsofi.taxi.io.Csv
import org.joda.time.DateTime

object DataWriter {

  def writeResultData(): Unit = {
    val timestamp: String = TimeHelper.dateToString(DateTime.now())
    val resultFolderPath: String = s"output/${timestamp.toString}"
    val resultFolder: File = new File(resultFolderPath)
    if (! resultFolder.exists()) resultFolder.mkdirs()
    val rideDataFilePath: String = s"$resultFolderPath/ride_data.csv"
    writeRideData(rideDataFilePath)
  }

  private def writeRideData(filePath: String): Unit = {
    val header: Array[String] = Array("month", "taxi_rides", "uber_rides")
    val rows: List[Array[String]] = DataHelper.collectMonthlyRideNumbers
    Csv.writeToCsv(filePath, header, rows)
  }

}
