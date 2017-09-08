package CountErrorMessage

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "messages.log"
    val conf = new SparkConf().setAppName("Count Error Messages").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val errors = logData.filter(line => line.contains("[error]")).count()
    println("Lines with [error]:"+errors)
    sc.stop()
  }
}