
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._
import scala.math.max

import sun.util.logging.PlatformLogger.Level
object tempInfo {
  def main(args: Array[String]): Unit = {
    def patten(Lines:String)={

      //  // Set the log level to only print errors
       // Logger.getLogger("org").setLevel(Level.ERROR)

      val field=Lines.split(",")
      val station_id=field(0)
      val TType=field(2)
      val Temp=field(3)
      (station_id,TType,Temp)

    }
//Find the Minimum Temperature for the Station ID : ITE00100554
    val sc=new SparkContext("local[2]","TemperatureInfo")
    val FilePath=sc.textFile("1800.csv")
    val finalDf=FilePath.map(patten)
    val sd1=finalDf.filter(c => c._1 == "TMAX")
    val sd2=sd1.map(x=>(x._1,x._2.toFloat))
    val maxTempsByStation = sd2.reduceByKey( (x,y) => max(x,y))
    val finalMax=maxTempsByStation.collect()


    for(result<-finalMax){
     println(result)
    }


  }
}
