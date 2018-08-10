import org.apache.spark.SparkContext
import org.spark_project.jetty.util.Fields


object airLinesDataExample {

	def main(args: Array[String]): Unit = {
	  ///Edureka/Project/Airlines/Air-datasets/airports_mod.dat
	  
/*	    0.         1       2              3             4          5.      6         7          8          9        10   11 

Airport id , Name ,   city ,      country      , IATA/FAA , ICAO , LATITUDE ,LONGITUDE , ALTITUDE , TIMEZONE , DST , TZ

    1	   ,Goroka,  Goroka,  Papua New Guinea,    GKA    ,AYGA  ,-6.081689 ,145.391881, 5282,        10     ,  U  ,pacific
	  */
	//A. Find list of Airports operating in the Country India  
	  
	  val sc=new SparkContext("local[*]","airlines data")
	/*  val data=sc.textFile("airports_mod.dat")
	  //data.foreach(x=>println(x))
	  val splitData=data.filter(x=> x.split(",")(3).equalsIgnoreCase("india"))
	  val t1=splitData.map(b=>{
	    val t2=b.split(",")
	    (t2(0),t2(1),t2(3),t2(4),t2(5))
	    
	  })*/
	  
	 // val x=t1.foreach(x=> println(x))
	  
//set 2
	  
	  //dataset1
	  val finalAirLines=sc.textFile("Final_airlines")
	  //dataset2
	  val routeRdd =sc.textFile("routes.dat")
	  
	  val s2=finalAirLines.map(x=> {
	    val s1=x.split(",")
	    (s1(0),s1(1))
	    })
	  
	  val s3=routeRdd.map(y=>{
	   val r=y.split(",")
	   (r(1),r(7))
	   
	   
	 })
	 
	 val apps=s2.join(s3).collect()
	 val filterApps=apps.filter(o => o._2._2.equals("0"))
	 val AirlinesListPairRDD  = filterApps.map(o => {
	(o._2._1, o._2._2)
	  })
	 // val AirlinesCountRDD = AirlinesListPairRDD.foreach(x=>println(x))

	  //set 3  Which country (or) territory having highest Airports

	  val airMod=sc.textFile("airports_mod.dat")
	  println("to find highest airports")
	  val splitMod=airMod.map(x=>{
	    val fields=x.split(",")
	    (fields(0).toInt,fields(3))
	    
	    
	  })
	  val test=splitMod.distinct().first()._2
	  print(test)
	  //foreach(x=>println(x))
	  
	}

}