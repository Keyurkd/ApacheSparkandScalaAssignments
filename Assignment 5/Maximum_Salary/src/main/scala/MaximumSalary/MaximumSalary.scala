package MaximumSalary

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]) {
    val salaryFile = "salary.csv"
    val conf = new SparkConf().setAppName("Count Error Messages").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sparkSession = SparkSession.builder
      .config(conf = conf)
      .appName("Count Error Messages")
      .getOrCreate()
    val df = sparkSession.read.option("header", "true").
      csv(salaryFile)
    val maximumSalary = df.groupBy("Salary").max().first()
    println("Maximum Salary is " + maximumSalary)
    sc.stop()
  }
}