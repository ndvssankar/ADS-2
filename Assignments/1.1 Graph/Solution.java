import java.util.*;

public class Solution {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String type = scan.nextLine();
		int V = Integer.parseInt(scan.nextLine());
		int E = Integer.parseInt(scan.nextLine());

		String[] cities = scan.nextLine().split(",");
		LinearProbingHashST<Integer, String> st = new LinearProbingHashST();

		for (int i = 0; i < cities.length; i++) {
			st.put(i, cities[i]);
		}

		switch(type) {
			case "List":
			GraphList gl = new GraphList(V);
			while(scan.hasNext()) {
				String[] tokens = scan.nextLine().split(" ");
				gl.addEdge(Integer.parseInt(tokens[0]),
					Integer.parseInt(tokens[1]));
			}
			System.out.println(gl.V() + " vertices, " + gl.E() + " edges");
			for (int v = 0; v < gl.V(); v++) {
				StringBuffer sb = new StringBuffer();
				sb.append(st.get(v) + ": ");
				for (int w : gl.adj(v)) {
					sb.append(st.get(w) + " ");
				}
				System.out.println(sb.toString().trim());
			}
			break;
			case "Matrix":
			AdjMatrixGraph gm = new AdjMatrixGraph(V);
			while(scan.hasNext()) {
				String[] tokens = scan.nextLine().split(" ");
				gm.addEdge(Integer.parseInt(tokens[0]),
					Integer.parseInt(tokens[1]));
			}
			System.out.println(gm.V() + " vertices, " + gm.E() + " edges");
			for (int v = 0; v < gm.V(); v++) {
				StringBuffer sb = new StringBuffer();
				sb.append(st.get(v) + ": ");
				for (int w : gm.adj(v)) {
					sb.append(st.get(w) + " ");
				}
				System.out.println(sb.toString().trim());
			}
			break;
		}
	}
}