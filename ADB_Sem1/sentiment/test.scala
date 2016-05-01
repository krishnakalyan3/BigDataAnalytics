package eu.dmkm.adb.sentiment
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.twitter.TwitterUtils

object test {
  
  // nc -lk 9999
  
  def main(args: Array[String]): Unit = {
    val jarloc = "/Users/krishna/Documents/workspace/spark/target/spark-0.0.1-SNAPSHOT.jar"
    val conf = new SparkConf().setAppName("Test Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("spark://Krishna.local:7077")
    .setJars(List(jarloc))
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination() 
  }

}