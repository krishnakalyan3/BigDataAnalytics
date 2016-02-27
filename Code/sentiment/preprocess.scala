package eu.dmkm.adb.sentiment
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.TwitterFactory
import scala.io.Source
import org.apache.spark.sql.SQLContext

object preprocess {
def main(args: Array[String]): Unit = {
   val jarloc = "/Users/krishna/Documents/workspace/spark/target/spark-0.0.1-SNAPSHOT.jar"
    val conf = new SparkConf().setAppName("Twitter 2 Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local[4]")
    val Tdata = "/Users/krishna/Dropbox/DMKM/Course/ADB/Data/TD/*/*"
    val sc = new SparkContext(conf)
    val dataload = sc.textFile(Tdata, 2).cache()
    val wc = dataload.flatMap(line => line.split(" "))
    .map(word => (word ,1))
    .reduceByKey(_ + _).map(x => x._1 + "," + x._2)
    wc.coalesce(1).saveAsTextFile("/Users/krishna/Dropbox/DMKM/Course/ADB/Data/PreProcess")
}
}