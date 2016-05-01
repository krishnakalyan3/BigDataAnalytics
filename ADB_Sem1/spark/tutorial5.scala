package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial5 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(List((1,2),(3,4),(3,6)))
    
    //(1,3)
    //(3,5)
    //(3,7)
    rdd.mapValues(x =>x +1).foreach(println)
    
    //(1,2)
    //(1,3)
    //(1,4)
    //(1,5)
    //(3,4)
    //(3,5)
    rdd.flatMapValues(x => (x to 5)).foreach(println)
    
    //rdd.keys()
    //rdd.values()
    
    //(1,2)
    //(3,4)
    //(3,6)
    rdd.sortByKey().foreach(println)
  }

}