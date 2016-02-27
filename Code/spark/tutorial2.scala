package eu.dmkm.adb.spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial2 {
  def main(args: Array[String]): Unit = {
     
    val logFile = "/Users/krishna/spark/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    val sc = new SparkContext(conf)
    val input = sc.parallelize(List(1, 2, 3, 3))
    println("Map ")
    // [2,3,44]
    input.map(x => x+1).foreach(println)
    println("Flat Map ")
    // [1,2,3,2,3,3,3]
    input.flatMap(x => x.to(3)).foreach(println)
    println("Filter ")
    // [3,3]
    input.filter(x => x == 3).foreach(println)
    println("Distinct ")
    // [1,2,3]
    input.distinct().foreach(println)
    println("Sample ")
    // [1,3]
    input.sample(false,0.5).foreach(println)
  }

}