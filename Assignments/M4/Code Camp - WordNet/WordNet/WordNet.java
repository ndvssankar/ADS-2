import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;


public class WordNet {

    private SAP sap;
    private Digraph graph;
    private ArrayList<String> alist;
    private LinearProbingHashST<String, Bag<Integer>> ht;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        ht = new LinearProbingHashST<String, Bag<Integer>>();
        alist = new ArrayList<String>();
        int v = processSynsets(synsets);
        this.graph = buildGraph(hypernyms, v);
        this.sap = new SAP(graph);
    }

    private int processSynsets(String synsets) {
        In in = new In(synsets);
        int count = 0;
        alist = new ArrayList<String>();
        while (in.hasNextLine()) {
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

    private Digraph buildGraph(String hypernyms, int v) {
        Digraph diGraph = new Digraph(v);
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] tokens = in.readLine().split(",");
            for (int i = 1; i < tokens.length; i++) {
                diGraph.addEdge(
                    Integer.parseInt(tokens[0]),
                    Integer.parseInt(tokens[i]));
            }
        }
        return diGraph;
    }

    private boolean isRootedDAG(Digraph diGraph) {
        int count = 0;
        for (int v = 0; v < diGraph.V(); v++) {
            if (diGraph.outdegree(v) == 0) {
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
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        return sap.length(ht.get(nounA), ht.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        int ancestor = sap.ancestor(ht.get(nounA), ht.get(nounB));
        if (ancestor == -1)
            throw new IllegalArgumentException("IllegalArgumentException");
        return alist.get(ancestor);
    }

    public void processQuery(String query) {
        String[] tokens = query.split(" ");
        String str = "";
        if (isNoun(tokens[0]) && isNoun(tokens[1])) {
            str = sap(tokens[0], tokens[1]);
            int distance = distance(tokens[0], tokens[1]);
            System.out.println("distance = " + distance + ", ancestor = " + str);
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}
