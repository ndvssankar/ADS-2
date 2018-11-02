
public class WordNet {

    Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        int V = processSynsets(synsets);
        graph = buildGraph(hypernyms, V);
    }

    private int processSynsets(String synsets) {
        In in = new In(synsets);
        int count = 0;
        while(in.hasNextLine()) {
            in.readLine();
            count++;
        }
        return count;
    }

    private Digraph buildGraph(String hypernyms, int V) {
        Digraph graph = new Digraph(V);
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] tokens = in.readLine().split(",");
            for (int i = 1; i < tokens.length; i++) {
                graph.addEdge(
                    Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[i]));
            }
        }
        return graph;
    }

    public void printGraph() {
        System.out.println(graph);
    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return null;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return 0;        
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}