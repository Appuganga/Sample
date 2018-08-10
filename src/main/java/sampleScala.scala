import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext



object sampleScala {
   def main(args: Array[String]): Unit = {
     val sc=new SparkContext("local[*]","airlines data")
   val sqlContext=new SQLContext(sc)
     val spark=sqlContext.sparkSession
     val file_Crime=sc.textFile("C://Users//KOGENTIX//Desktop//crime_data.csv")
       import spark.implicits._
     var header=file_Crime.first
     //val test=file_Crime.foreach(x=>println(x))
     var s1=file_Crime.filter(crime=>crime!=header)
     var s2=s1.map(x=>{
       var s3=x.split(",");
       (s3(0),s3(1))}).toDF("crime_type","crime_date")
       
      s2.registerTempTable("crimes")
 var sqlresult_crime_month=sqlContext.sql(" select count(1) crime_count,crime_type,cast(concat(substr(crime_date,7,4) ,substr(crime_date,0,2)) as Int) crime_month from  crimes group by  crime_type,cast(concat(substr(crime_date,7,4) ,substr(crime_date,0,2)) as Int) order by crime_month, crime_count desc")
      
    sqlresult_crime_month.show()
    sqlresult_crime_month.rdd.map(x=>x.mkString("\t")).saveAsTextFile("C://Users//KOGENTIX//Desktop//aparna")
    
       
   
       
       
       

     
  }
}