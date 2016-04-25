package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial6 {
  def main(args: Array[String]): Unit = {
    
    val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    // Spark Context
    val sc = new SparkContext(conf)
    // List with key values
    val rdd = sc.parallelize(List((1,2), (3,4), (3,6)))
    // 2nd List with key val
    val other = sc.parallelize(List((3,9)))
    // Subtract returns (1,2)
    rdd.subtractByKey(other).foreach(println)
    
    // Joins returns
    //  (3,(4,9))
    //  (3,(6,9))
    rdd.join(other).foreach(println)
    
    // Right Outer Join
    //  (3,(Some(4),9))
    //  (3,(Some(6),9))
    rdd.rightOuterJoin(other).foreach(println)
    
    // Left Outer Join
    //  (1,(2,None))
    //  (3,(4,Some(9)))
    //  (3,(6,Some(9)))
    rdd.leftOuterJoin(other).foreach(println)
    
    // Co-group
    // (1,(CompactBuffer(2),CompactBuffer()))
    // (3,(CompactBuffer(4, 6),CompactBuffer(9)))
    rdd.cogroup(other).foreach(println)
    
    // Reduce By Key 
    //  (1,2)
    //  (3,10)
    rdd.reduceByKey((x,y)=> x+y).foreach(println)
    
    //Group By key
    // (1,CompactBuffer(2))
    // (3,CompactBuffer(4, 6))
    rdd.groupByKey().foreach(println)
  }

}