object OddEven {
    def main(args: Array[String]) {
        val nums: List[Int] = List.range(1, 10)
        println("\n List  = " + nums);

        val evens = nums.filter(_ % 2 == 0)

        val odds = nums.filter(_ % 2 != 0)
        
        println("\n Even List = " + evens);
        println("\n Odd List = " + odds);
    }
}