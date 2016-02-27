package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.linalg.{Vector, Vectors}

object Tutorial1 {
 
  def main(args: Array[String]):Unit = {
  val logFile = "/Users/krishna/spark/README.md" // Should be some file on your system
  val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
  val sc = new SparkContext(conf)
  val lines = sc.textFile(logFile)
  // 98
  println(lines.count())
  // # Apache Spark
  println(lines.first())
  val  sparklines = lines.filter(x => x.contains("Spark"))
  // Spark is a fast and general cluster computing system for Big Data. It provides ....
  sparklines.foreach(println)
  val lines2 = sc.parallelize(List("pandas", "i like pandas"))
  // (1.5.2,Simple Application,org.apache.spark.SparkConf@64a4dd8d,Map(localhost:63764 -> (2061647216,2061631655)),local)
  println(sc.version,sc.appName,sc.getConf,sc.getExecutorMemoryStatus,sc.master)
  
  // takes the first 10 lines from the file
  println("Take example ")
  lines.take(10).foreach(println)
  
  // 1 4 9 16
  val input = sc.parallelize(List(1, 2, 3, 4))
  val result = input.map(x => x * x)
  result.foreach(println)
  // [1,4,9,16]
  println(result.collect().mkString(","))
  
  val lines3 = sc.parallelize(List("hello world", "hi"))
  val words = lines3.flatMap(line => line.split(" "))
  words.first() 
  // returns "hello"
  println(words.first())
  
  val line4 = sc.parallelize(List("coffee", "coffee","panda","monkey","tea"))
  val line5 = sc.parallelize(List("coffee","monkey","kitty"))
  val union1= line4.union(line5)
  // All ["coffee", "coffee","panda","monkey","tea","coffee","monkey","kitty"]
  println("Union of RDDs")
  union1.foreach(println) 
  // ["coffee","panda","monkey","tea"]
  println("Distinct of RDDs")
  line4.distinct().foreach(println)
  // [monkey,coffee]
  println("Intersection of RDDs")
  line4.intersection(line5).foreach(println)
  println("Subtraction of RDDs")
  // [tea,panda]
  line4.subtract(line5).foreach(println)
  println("Cartesian of RDDs")
  line4.cartesian(line4).foreach(println)
  /*
   (coffee,coffee)
(coffee,coffee)
(coffee,panda)
(coffee,monkey)
(coffee,tea)
(coffee,coffee)
(coffee,coffee)
(coffee,panda)
(coffee,monkey)
(coffee,tea)
(panda,coffee)
(panda,coffee)
(panda,panda)
(panda,monkey)
(panda,tea)
(monkey,coffee)
(monkey,coffee)
(monkey,panda)
(monkey,monkey)
(monkey,tea)
(tea,coffee)
(tea,coffee)
(tea,panda)
(tea,monkey)
(tea,tea)
*/
  
  
  }
}