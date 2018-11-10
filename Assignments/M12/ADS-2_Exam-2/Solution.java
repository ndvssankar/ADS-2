import java.util.*;
import edu.princeton.cs.algs4.*;
public class Solution {

	public static void main(String[] args) {
		// Self loops are not allowed...
		// Parallel Edges are allowed...
		// Take the Graph input here...

		Scanner scan = new Scanner(System.in);
		int numberOfVertices = scan.nextInt();
		int numberOfEdges = scan.nextInt();
		EdgeWeightedGraph graph = new EdgeWeightedGraph(numberOfVertices);
		while(scan.hasNextInt()) {
			int v = scan.nextInt();
			int w = scan.nextInt();
			int c = scan.nextInt();
			graph.addEdge(new Edge(v, w, c));
		}
		scan.nextLine();
		String caseToGo = scan.nextLine();
		switch (caseToGo) {
		case "Graph":
			System.out.println(graph);
			break;

		case "DirectedPaths":
			// Handle the case of DirectedPaths, where two integers are given.
			// First is the source and second is the destination.
			// If the path exists print the distance between them.
			// Other wise print "No Path Found."
			int s = scan.nextInt();
			int t = scan.nextInt();
			DijkstraUndirectedSP sp = new DijkstraUndirectedSP(graph, s);
			if (!sp.hasPathTo(t))
				System.out.println("No Path Found.");
			else
				System.out.println(sp.distTo(t));
			break;

		case "ViaPaths":
			// Handle the case of ViaPaths, where three integers are given.
			// First is the source and second is the via is the one where path should pass throuh.
			// third is the destination.
			// If the path exists print the distance between them.
			// Other wise print "No Path Found."
			s = scan.nextInt();
			int v = scan.nextInt();
			t = scan.nextInt();
			sp = new DijkstraUndirectedSP(graph, s);
			DijkstraUndirectedSP sp1 = new DijkstraUndirectedSP(graph, v);
			if (!sp.hasPathTo(t)) {
				System.out.println("No Path Found.");
			} else {
				System.out.println(sp.distTo(v) + sp1.distTo(t));
				StringBuffer sb = new StringBuffer();
				sb.append(s + " ");
				for (Edge e : sp.pathTo(v)) {
					sb.append(e.either() + " ");
				}
				for (Edge e : sp1.pathTo(t)) {
					v = e.other(v);
					sb.append(v + " ");	
				}
				System.out.println(sb.toString().trim());
			}
			break;

		default:
			break;
		}

	}
}