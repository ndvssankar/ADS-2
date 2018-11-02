
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

    private boolean isRootedDAG(Digraph graph) {
        int count = 0;
        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0) {
                count++;
            }
        }
        if (count == 1)
            return true;
        return false;
    }

    public void printGraph() {
        DirectedCycle dc = new DirectedCycle(graph);
        if (dc.hasCycle()) {
            System.out.println("Cycle detected");
        } else if (!isRootedDAG(graph)) {
            System.out.println("Multiple roots");
        } else {
            System.out.println(graph);
        }
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

    public void processQueries(String query) {
        String[] tokens = query.split(" ");
        if (tokens[0].equals("null") || tokens[1].equals("null")) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
