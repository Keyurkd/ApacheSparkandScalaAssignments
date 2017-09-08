package MovieDataAnalysis

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SimpleApp {
  def main(args: Array[String]) {
    val movieData = "movies_dataset.csv"
    val conf = new SparkConf().setAppName("Movie Data Analysis").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val sparkSession = SparkSession.builder
      .config(conf = conf)
      .appName("Movie Data Analysis")
      .getOrCreate()
    val df = sparkSession.read.option("header", "true").
      csv(movieData)
    val moviesDF = df.repartition(4)

    moviesDF.registerTempTable("moviesTable")

    //How many horror movies were there between the year 1952 and 1968.
    val horrorMovieDF = sqlContext.sql("select COUNT(*) from moviesTable WHERE (Year BETWEEN 1952 AND 1968 AND Genre=='Horror')")
    horrorMovieDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("horrorMovieDF.csv")

    //How many movies were released for each year
    val eachYearMovieDF = sqlContext.sql("select Year, COUNT( Year) from moviesTable group by Year ORDER BY Year ASC")
    eachYearMovieDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("eachYearMovieDF.csv")

    //How many movie’s length greater than 100 min.
    val movieLengthDF = sqlContext.sql("select COUNT(*) from moviesTable WHERE Length > 100")
    movieLengthDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("movieLengthDF.csv")

    //List the awarded movie director names and title.
    val awardsMovieDF = sqlContext.sql("select Title, Director from moviesTable WHERE Award=='Yes'")
    awardsMovieDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("awardsMovieDF.csv")

    //Find the name of the actor who have worked more than 1 movie.
    val actorDF = sqlContext.sql("select Actor, COUNT(Actor) as Count from moviesTable GROUP BY Actor Having Count>1")
    actorDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("actorDF.csv")

    //List the title of the movies whose popularity reach above 60.
    val popularMoviesDF = sqlContext.sql("select Title from moviesTable WHERE Popularity>60")
    popularMoviesDF.coalesce(1).
      write.
      format("com.databricks.spark.csv").
      option("header", "true").
      save("popularMoviesDF.csv")

    sc.stop()
  }
}