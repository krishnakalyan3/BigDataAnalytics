package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial7 {
  def main(args: Array[String]): Unit = {
     val conf = new SparkConf().setAppName("Little Log")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    // Spark Context
    val sc = new SparkContext(conf)
    // Read the file
    val logFile = "/Users/krishna/Dropbox/DMKM/Course/ADB/Data/littlelog.csv"
    // Read file
    val file = sc.textFile(logFile)
    // Print Every thing
    file.collect().foreach(println)
    
    // Check if length > 0
    val fltr = file.filter(_.length > 0)
    // Filter
    val keys = fltr.map(_.split(",")).map(k => k(5))
    //key value
    val stateCnt = keys.map(key => (key,1))
    // Count by key
    val lastMap = stateCnt.countByKey
    // Print every thing on output
    lastMap.foreach(println)
  }

}