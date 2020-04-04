package hu.corvinus.kt.zsofi.taxi.io

import java.io.FileWriter
import scala.collection.JavaConverters._
import com.opencsv.CSVWriter


object Csv {

  def writeToCsv(filePath: String, header: Array[String], rows: List[Array[String]]): Unit = {
    val writer: CSVWriter = new CSVWriter(new FileWriter(filePath))
    val writerInput: List[Array[String]] = header +: rows

    try {
      writer.writeAll(writerInput.asJava)
    } catch {
      case e: Exception => throw e
    } finally {
      writer.close()
    }
  }

}
