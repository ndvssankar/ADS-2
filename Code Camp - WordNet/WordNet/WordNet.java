import java.util.ArrayList;

public class WordNet {

    private SAP sap;
    private Digraph graph;
    private ArrayList<String> alist;
    private LinearProbingHashST<String, Bag<Integer>> ht;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        ht = new LinearProbingHashST<String, Bag<Integer>>();
        alist = new ArrayList<String>();
        int V = processSynsets(synsets);
        graph = buildGraph(hypernyms, V);
    }

    private int processSynsets(String synsets) {
        In in = new In(synsets);
        int count = 0;
        alist = new ArrayList<String>();
        while(in.hasNextLine()) {
            String[] tokens = in.readLine().split(",");
            String[] nouns = tokens[1].split(" ");
            Bag<Integer> bag;
            alist.add(Integer.parseInt(tokens[0]), tokens[1]);
            for (String noun : nouns) {
                if (ht.contains(noun)) {
                    bag = ht.get(noun);
                } else {
                    bag = new Bag<Integer>();
                }
                bag.add(Integer.parseInt(tokens[0]));
                ht.put(noun, bag);
            }
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
        return (count == 1) ? true : false;
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
        return ht.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word.equals("null")) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        return ht.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(ht.get(nounA), ht.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int ancestor = sap.ancestor(ht.get(nounA), ht.get(nounB));
        if (ancestor == -1)
            throw new IllegalArgumentException("IllegalArgumentException");
        return alist.get(ancestor);
    }

    public void processQuery(String query) {
        String[] tokens = query.split(" ");
        String str = "";
        if (isNoun(tokens[0]) && isNoun(tokens[1])) {
            sap = new SAP(graph);
            str = sap(tokens[0], tokens[1]);
            int distance = distance(tokens[0], tokens[1]);
            System.out.println("distance = " + distance + ", ancestor = " + str);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
