import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.functions._

import scala.collection.mutable

object OlympicsMedalistAnalysis {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("Movie Analysis").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc);
    val dataset = sc.textFile("./Data/olympic_medalists_dataset.csv")


    var dataList = new mutable.ArrayBuffer[StructField];
    dataList += StructField("city",StringType,true)
    dataList += (StructField("year",IntegerType,true))
    dataList += (StructField("sport_type",StringType,true))
    dataList += (StructField("discipline",StringType,true))
    dataList += (StructField("athlete",StringType,true))
    dataList += (StructField("country_code",StringType,true))
    dataList += (StructField("gender",StringType,true))
    dataList += (StructField("event",StringType,true))
    dataList += (StructField("m",StringType,true))
    dataList += (StructField("medal",StringType,true))


    val schema =
      StructType(dataList)
    val rowRDD = dataset.map(_.split(",")).map(p => Row(p(0), p(1).toInt,p(2),p(3),p(4),p(5),p(6),p(7),p(8),p(9)))

    val dataFrame = sqlContext.createDataFrame(rowRDD, schema)

    dataFrame.registerTempTable("olympics")


    val atheletsFromBejing  = dataFrame.filter(col("city") ==="Beijing").count()
    val atheletsFromBejingWhoWonGoldMedal = dataFrame.filter(col("city") ==="Beijing" && col("medal")==="Gold").count()
    println(" Find out what percentage of athletes from “Beijing” appeared in Olympics of 2008, won gold medals irrespective of the type of the evens?  ")
    println("percetage :"+((atheletsFromBejingWhoWonGoldMedal*1.0/atheletsFromBejing*1.0) *100))


    println("Find the athlete name, who has won highest number of gold  :")
    val athlete = dataFrame.map(t => (t(4),1)).reduceByKey(_ + _ ).reduce((a,b) => (if (a._2>b._2) a else b) )
    println(athlete._1 +" "+ athlete._2)
    println("list the years in which he appeared in Olympics. ")
    dataFrame.filter(col("athlete") === athlete._1).select(col("year")).distinct.show()
    val indians =dataFrame.filter(col("year") >1900 &&  col("year")<1948 && col("country_code") === "IND")
    println("How many Indians won gold in between 1900 to 1948?  ")
    indians.show()
  }
}
