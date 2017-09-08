import collection.mutable.ArrayBuffer
object OddEven {
    def main(args: Array[String]) {
        val arrBuf = ArrayBuffer(1, 2, 3)
        print("\n ArrayBuffer  = " + arrBuf);

        val arr = Array(4, 5, 6)
        print("\n Array  = ");
        arr.foreach(elem => {
                print(elem + "  ")
            }
        )

        val merged = arrBuf ++ arr;

        println("\n Merged  = " + merged);
    }
}