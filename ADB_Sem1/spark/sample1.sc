package eu.dmkm.adb.spark

object sample1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  3 + 5                                           //> res0: Int(8) = 8
  var x = 5                                       //> x  : Int = 5
	val nums = List(1,2,3,4,5,6,7,8,9,10)     //> nums  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
 	println(nums)                             //> List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
 	var mul = ((x: Int) => x %5 )             //> mul  : Int => Int = <function1>
  println(mul(5))                                 //> 0
	(1 to 5).reduceLeft(_+_)                  //> res1: Int = 15
	(1 to 5).map(x => x* x)                   //> res2: scala.collection.immutable.IndexedSeq[Int] = Vector(1, 4, 9, 16, 25)
	
}