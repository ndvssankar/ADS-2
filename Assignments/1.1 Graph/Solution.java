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
			if (gl.V() <= 1)
				System.out.println("No edges");
			else {
				for (int v = 0; v < gl.V(); v++) {
					StringBuffer sb = new StringBuffer();
					sb.append(st.get(v) + ": ");
					for (int w : gl.adj(v)) {
						sb.append(st.get(w) + " ");
					}
					System.out.println(sb.toString());
				}
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
			if (gm.V() <= 1) {
				System.out.println("No edges");
			} else {
				for (int v = 0; v < gm.V(); v++) {
					StringBuffer sb = new StringBuffer();
					for (int w = 0; w < gm.V(); w++) {
						if (gm.contains(v, w))
							sb.append(1 + " ");
						else
							sb.append(0 + " ");
					}
					System.out.println(sb.toString());
				}
			}
			break;
		}
	}
}