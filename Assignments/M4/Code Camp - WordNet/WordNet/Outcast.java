import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

   /**
    * Wordnet object.
    */
   WordNet wordnet;

   // constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
      this.wordnet = wordnet;
   }
   
   /**
    * Calculates the distance between xi to every other noun
    * from the given list of nouns. Distance between xi and xi
    * is ignore as it is 0.
    * @param  index index of the specific noun.
    * @param  nouns list of nouns.
    * @return       the sum of all the distances.
    */
   private int calculateDistance(int index, String[] nouns) {
      int distance = 0;
      for (int i = 0; i < nouns.length; i++) {
         if (i != index)
            distance += wordnet.distance(nouns[index], nouns[i]);
      }
      return distance;
   }
   /**
    * outcast a noun from a list of nouns given.
    * @param  nouns the list of nouns.
    * @return       an outcast.
    */
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) {
      int distance = Integer.MIN_VALUE;
      int ancestor = -1;
      for (int i = 0; i < nouns.length; i++) {
         int di = calculateDistance(i, nouns);
         if (distance < di) {
            ancestor = i;
         }
      }
      return nouns[ancestor];
   }

   public static void main(String[] args) {
      WordNet wordnet = new WordNet(args[0], args[1]);
      Outcast outcast = new Outcast(wordnet);
      for (int t = 2; t < args.length; t++) {
         In in = new In(args[t]);
         String[] nouns = in.readAllStrings();
         StdOut.println(args[t] + ": " + outcast.outcast(nouns));
      }
   }
}