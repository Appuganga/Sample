import org.apache.spark.SparkContext
import org.apache.spark._
object friendsByAge {
  def main(args: Array[String]): Unit = {


    def mapping(lines:String)={
      val field=lines.split(",")
      val age=field(1)
      val numberOfFriends=field(2)
      (age,numberOfFriends)

    }

   // Create a SparkContext using every core of the local machine


    val sc =new SparkContext("local[*]","FriendsData")
    val filedata=sc.textFile("fakeFriend.csv")
    val both=filedata.map(mapping)
//doubt
    val totalByAge=both.mapValues(x=>(x,1)).reduceByKey((x,y)=>(x._1+y._1,x._2+y._2))
    totalByAge.foreach(x=>println(x))

    //val avrgByAge=totalByAge.mapValues(x => x._1 / x._2)

}
}
