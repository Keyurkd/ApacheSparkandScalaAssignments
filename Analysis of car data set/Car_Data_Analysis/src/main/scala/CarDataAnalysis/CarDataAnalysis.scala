package CarDataAnalysis

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SimpleApp {
  def main(args: Array[String]) {
    val carData = "cars_dataset.csv"
    val conf = new SparkConf().setAppName("Car Data Analysis").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val sparkSession = SparkSession.builder
      .config(conf = conf)
      .appName("Car Data Analysis")
      .getOrCreate()
    val df = sparkSession.read.option("header", "true").
      csv(carData)
    val carsDF = df.repartition(4)

    // How many cars developed by per country?
    val countDistinctDF = carsDF.select("Origin").groupBy("Origin").count().distinct
    countDistinctDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionCountDistinctDF.csv")

    //What is the Minimum, Maximum and average Numbers of a particular car Model developed by per country?
    val modelsByCountryDF = carsDF.select("Model", "Origin").groupBy("Model", "Origin").count().distinct
    modelsByCountryDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionModelsByCountryDF.csv")

    carsDF.registerTempTable("carsTable")

    //Calculate the top 10 heavy cars from dataset
    val heavyCarDF = sqlContext.sql("select * from carsTable ORDER BY Weight DESC LIMIT 10")
    heavyCarDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionHeavyCarDF.csv")

    //How many cars have 4 Cylinder engines and which have been developed in US?
    val cylinderEngineDF = sqlContext.sql("select COUNT(*) from carsTable WHERE (Cylinders=4 AND Origin = 'US')")
    cylinderEngineDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionCylinderEngineDF.csv")

    //Acceleration more than 23.5
    val accelerationDF = sqlContext.sql("select * from carsTable WHERE Acceleration>=23.5")
    accelerationDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionAccelerationDF.csv")

    //Weight more than 3000
    val weightDF = sqlContext.sql("select * from carsTable WHERE Weight>=3000")
    weightDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionWeightDF.csv")

    //And MPG more than 25.0
    val MPGDF = sqlContext.sql("select * from carsTable WHERE MPG>=25.0")
    MPGDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("SolutionMPGDF.csv")

    sc.stop()
  }
}