object GCD {
    def main(args: Array[String]) {
        var a = 21;
        var b = 9;
        var gcd = 0;
        println("\n Number 1 = " + a);
        println("\n Number 2 = " + b);

        while (a != 0) {
            gcd = a;
            a = b % a;
            b = gcd;
        }
        
        println("\n GCD = " + gcd);
    }
}