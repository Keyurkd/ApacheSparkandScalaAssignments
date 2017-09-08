object WordCount {
    def main(args: Array[String]) {
        val sentence = "I scream, you scream, we all scream for icecream!";
        val wordToCount="scream";
        println("\nSentence = '"+sentence+"'");

        val words=sentence.split("\\W+");

        println("\nWord to count = '"+wordToCount+"'");

        val wordCount=words.count(_ == wordToCount);
        
        println("\nWord count of '"+wordToCount+"' is "+wordCount);
    }
}