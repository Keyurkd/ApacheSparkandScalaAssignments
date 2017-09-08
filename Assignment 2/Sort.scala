object Sort {
    def main(args: Array[String]) {
        val arr = Array(1, 4, 6, 3, 8, 4, 8, 3, 0, 9, 4, 7, 1);
        println("\nElements before sorting:");
        arr.foreach(elem => {
                print(elem + "  ")
            }
        )

        val sortedArr=arr.sortWith(_ < _)
        
        println("\n\nElements after sorting:");
        sortedArr.foreach(elem => {
                print(elem + "  ")
            }
        )
    }
}