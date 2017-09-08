class Dog {
  def bark(): Unit ={
    println("bow bow")
  }
}
trait Cat {
  def growl(): Unit = {
    println("meeow ")
  }


}

class Animal extends Dog with Cat{

}
