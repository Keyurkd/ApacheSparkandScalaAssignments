package FilterLine

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Filter Line").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val wordsToFilter = Array("Spark")
    val sentence1 = "Apache Spark is a fast"
    val sentence2 = "It’s easy to run locally"
    val sentence3 = "It provides high-level APIs in Java, Scala, Python"
    val sentence4 = "Spark runs on both Windows and UNIX"
    val filterFrom = Array(sentence1, sentence2, sentence3, sentence4)
    def check(s: String, l: Array[String]): Boolean = {
      var temp: Int = 0
      for (element <- l) {
        if (element.equals(s)) { temp = temp + 1 }
      }
      var result = false
      if (temp > 0) { result = true }
      result
    }
    val singles = sc.parallelize(wordsToFilter)
    val sentence = sc.parallelize(filterFrom)
    val result = singles.cartesian(sentence)
      .filter(x => check(x._1, x._2.split(" ")) == true)
      .groupByKey()
      .map(x => (x._2.mkString("\n")))
    result.foreach(println)
    sc.stop()
  }
}