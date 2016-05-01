package eu.dmkm.adb.spark
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object tutorial4 {
def main(args: Array[String]): Unit = {
   val conf = new SparkConf().setAppName("Simple Application")
    .setSparkHome("/Users/krishna/spark")
    .setMaster("local")
    val sc = new SparkContext(conf)
   val rdd = sc.parallelize(List(1,2,3,4,5,5))
   
   // 20
   val sum = rdd.reduce((x,y) => x+y)
   println(sum)
   
   //  (20,6)
   val result = rdd.aggregate((0, 0))(
    (acc, value) => (acc._1 + value, acc._2 + 1),
    (acc1, acc2) => (acc1._1 + acc2._1, acc1._2 + acc2._2))
   
   // 3.3333333333333335
   val avg = result._1 / result._2.toDouble
   println(result)
   println(avg)
   rdd.collect()
   
   //  1
   //  2
   //  3
   rdd.take(3).foreach(println)
   
   // 5
   // 5
   rdd.top(2).foreach(println)
   
   //(5,2)
   //(1,1)
   //(2,1)
   //(3,1)
   //(4,1)
   rdd.countByValue().foreach(println)
   
  //1
  //2
  //3
   rdd.takeOrdered(3).foreach(println)
  
   // 20
   print(rdd.fold(0)((x,y) => x+y))
   
   // 20
   print(rdd.reduce((x,y)=>x+y))
   
   //1
   //2
   //3
   //4
   //5
   //5
   rdd.foreach(println)
   
}
}