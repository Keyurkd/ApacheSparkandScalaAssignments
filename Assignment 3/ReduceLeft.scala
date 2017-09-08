object ReduceLeft {
    def main(args: Array[String]) {
        val a = Array(23, 53, 8, 24, 94, 45)
        print("Array elements : ");
        a.foreach(elem => print(elem + " "))
        val sum = a.reduceLeft(_ + _)
        println("\nSum : " + sum);
    }
}