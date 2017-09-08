import collection.mutable.Map;
object Map {
    def main(args: Array[String]) {
        var capitals = Map[String, String]();
        capitals += ("Rajasthan" -> "Jaipur", "Karnataka" -> "Bengaluru");
        println(capitals);
        capitals -= "Rajasthan"
        println(capitals);
    }
}