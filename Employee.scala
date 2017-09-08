
class Employee {

  private var salary =10000;
  def increment(incr :Int):Int={
    salary = salary + incr;
    return salary;
  }
  def currentSal():Int = {
    return  salary;
  }

}
