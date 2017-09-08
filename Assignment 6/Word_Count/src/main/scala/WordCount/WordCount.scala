package WordCount

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val wordFile = "words.txt"
    val conf = new SparkConf().setAppName("Word Count").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val wordData = sc.textFile(wordFile, 2).cache()
    val words = wordData.flatMap(l => l.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    words.saveAsTextFile("Word_Count_Output")
    sc.stop()
  }
}