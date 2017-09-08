package PairRDD

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("PairRDD").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Seq(("Dharmesh", "Delhi", "302020"), ("John", "Bangalore", "505659")))
    val rdd2 = sc.parallelize(Seq(("Dharmesh", "Delhi", "Lal Bagh"), ("John", "Bangalore", "Indira Nagar")))
    val pairRDD1 = rdd1.map { case (name, address, zipcode) => ((name, address), zipcode) }
    val pairRDD2 = rdd2.map { case (name, address, landmark) => ((name, address), landmark) }
    val joined = pairRDD1.fullOuterJoin(pairRDD2).map {
      case ((name, address), (zipcode, landmark)) => (name, address, zipcode.mkString(" "), landmark.mkString(" "))
    }
    println(joined.collect().foreach(println))
    sc.stop()
  }
}