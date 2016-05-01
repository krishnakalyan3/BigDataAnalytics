package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf


object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "/Users/krishna/spark/README.md" // Should be some file on your system
    val jarloc = "/Users/krishna/Documents/workspace/spark/target/spark-0.0.1-SNAPSHOT.jar"
    val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    //.setMaster("spark://Krishna.local:7077")
    .setMaster("local")
    .setJars(List(jarloc))
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    
    logData.collect().foreach(println)
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
    
    val count = logData
    .flatMap(line => line.split(" "))
    .map(word => (word ,1))
    .reduceByKey(_ + _)
    count.foreach(println)
    
    
  }
}