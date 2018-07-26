import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark._

import org.apache.spark.sql._
import org.apache.log4j._

object movieData {
  case class movies(userid:Int,movieid:Int,rating:Int,timestamp:Int)
def mapper(line:String): movies = {
    val fields = line.split(',')
      println(fields)

    val mv:movies = movies(fields(0).toInt, fields(1).toInt, fields(2).toInt, fields(3).toInt)
    return mv
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .appName("SparkSQL")
      .master("local[*]")
      //.config("spark.sql.warehouse.dir", "file:///C:/temp") // Necessary to work around a Windows bug in Spark 2.0.0; omit if you're not on Windows.
      .getOrCreate()

    import spark.implicits._
    val lines = spark.sparkContext.textFile("movie.txt")
    val lineApp=lines.map(mapper).toDS().cache()
    lineApp.printSchema()
    // Convert our csv file to a DataSet, using our Person case
    // class to infer the schema.
    print("select query")
   //val s= lineApp.select(col = "userid","movieid","rating","timestamp").show()

    //val s_filter=lineApp.filter(lineApp("rating")>= 4).show()

    //print("group by movieid and count")
   // val s_count=lineApp.groupBy("movieid").max("rating").show()

    val r=lineApp.select(lineApp("movieid"),lineApp("rating")+10).show()





   // s.foreach(x=>print(x))

    spark.stop()


  }
}
