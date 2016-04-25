package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial3 {
def main(args: Array[String]): Unit = {
    val logFile = "/Users/krishna/spark/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    val sc = new SparkContext(conf)
    val input1 = sc.parallelize(List(1, 2, 3))
    val input2 = sc.parallelize(List(3, 4, 5))
    
    // [1,2,3,3,4,5]
    input1.union(input2).foreach(println)
    // [3]
    input1.intersection(input2).foreach(println)
    //[1,2] Subtract Set Intersection
    input1.subtract(input2).foreach(println)
    //(1,3)(1,4)(1,5)(2,3)(2,4)(2,5)(3,3)(3,4)(3,5)
    input1.cartesian(input2).foreach(println)
    println("Numeric RDD Operations ")
    // 3
    println(input1.count())
    // 2
    input1.mean()
    // 6
    input1.sum()
    // 3
    input1.max()
    // 1
    input1.min()
    // 0.6666666666666666
    println(input1.variance())
    input1.sampleVariance()
    // 0.816496580927726
    println(input1.stdev())
    // 1
    println(input1.sampleStdev())
    
    val rdd = sc.parallelize(List(1, 2, 3,3))
    val other = sc.parallelize(List(3,4,5))
    rdd.intersection(other).foreach(println)
    
}
}