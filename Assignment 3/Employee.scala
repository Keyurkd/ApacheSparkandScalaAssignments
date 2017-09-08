class Employee(val x: Int) {
    private var salary: Int = x

    def increment(x: Int) {
        salary += x
        println("Salary increment by "+x);
    }
    def currentSal() {
        println("Current salary "+salary);
    }
}

object Demo {
    def main(args: Array[String]) {
        val Dharmesh = new Employee(30000);
        Dharmesh.currentSal();
        Dharmesh.increment(10000);
        Dharmesh.currentSal();
    }
}