import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object MostPopularSuperhero {

  // Function to extract hero ID -> hero name tuples (or None in case of failure)
  def heroNames(lines: String): Option[(Int, String)] = {
    val parthero = lines.split('\"')
    if (parthero.length > 1) {
      return Some(parthero(0).trim().toInt, parthero(1))
    } else {
      return None // flatmap will just discard None results, and extract data from Some results.
    }
  }

  // Function to extract the hero ID and number of connections from each line
  def countCoOccurences(line: String) = {
    var elements = line.split("\\s+")
    ( elements(0).toInt, elements.length - 1 )
  }


  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]", "MostPopularSuperhero")

    // Build up a hero ID -> name RDD
    val df1 = sc.textFile("Marvel-names.txt")
    val names_rdd = df1.flatMap(heroNames)
    //names_rdd.foreach(x => println(x))

    //Load up the superhero co-apperarance data
    val df2 = sc.textFile("Marvel-graph.txt")
    //df2.foreach(y=>println(y))
    val pairings = df2.map(countCoOccurences)

   val totalFriendsByCharacter = pairings.reduceByKey( (x,y) => x + y )
     //.foreach(x => println(x))

    val flipped = totalFriendsByCharacter.map( x => (x._2, x._1))
    val mostPopular = flipped.max()
    println(mostPopular)
    val res=names_rdd.lookup(mostPopular._2)(0)

    println(s"$res is the most popular superhero with ${mostPopular._1} co-appearances.")

  }

}