import org.apache.spark.SparkContext

object wordCountBetter {

  def main(args: Array[String]): Unit = {
    val sc=new SparkContext("local[*]","wordCountExample")
    val fileTxt=sc.textFile("book.txt")
    //fileTxt.foreach(x=> println(x))
  val ex=fileTxt.flatMap(x=>x.split("\\W+"))
    val exLower=ex.map(x=>x.toLowerCase)
   // val finalWordCount=exLower.countByValue().foreach(x=>println(x))    -------> 1st method to find word count

    val reduceFile=exLower.map(x=>(x,1)).reduceByKey((x,y) => x+y)
      //.foreach(x=>println(x))
    //flip the (word,count) to (count,word) and sort by key

    val sortTheKey=reduceFile.map(x=>(x._2,x._1)).sortByKey()

    for(x <- sortTheKey){
      val word=x._2
      val count=x._1
      println(s"$word and $count")
    }


  }

}
