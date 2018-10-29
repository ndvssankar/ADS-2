

public class GraphList {
	private int V;
	private int E;
	private Bag<Integer>[] adj;

	public GraphList(int V) {
		this.V = V;
		adj = new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = (Bag) new Bag<Integer>();
		}
	}

	private boolean isSelfLoop(int v, int w) {
		return v == w;
	}

	private boolean isParallelEdge(int v, int w) {
		for (int e : adj(v)) {
			if (e == w)
				return true;
		}
		return false;
	}

	public void addEdge(int v, int w) {
		if (isSelfLoop(v, w)) {
			return;
		}

		if (isParallelEdge(v, w)) {
			return;
		}

		adj[v].add(w);
		adj[w].add(v);
		E++;
	}

	public int degree(int v) {
		int count = 0;
		for (int i : this.adj[v])
			count++;
		return count;
	}

	public int V() {
		return this.V;
	}

	public int E() {
		return this.E;
	}

	public Iterable<Integer> adj(int v) {
		return adj[v];
	}

}