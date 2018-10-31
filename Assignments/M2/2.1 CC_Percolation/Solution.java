// public class Percolation {
//    public Percolation(int n)                // create n-by-n grid, with all sites blocked
//    public    void open(int row, int col)    // open site (row, col) if it is not open already
//    public boolean isOpen(int row, int col)  // is site (row, col) open?
//    public boolean isFull(int row, int col)  // is site (row, col) full?
//    public     int numberOfOpenSites()       // number of open sites
//    public boolean percolates()              // does the system percolate?
// }


// You can implement the above API to solve the problem


import java.util.*;

class Percolate {

    int openSiteCount;
    boolean[][] arr;
    Graph graph;
    int n;

    // create n-by-n grid, with all sites blocked
    public Percolate(int n) {
        this.n = n;
        arr = new boolean[n][n];
        graph = new Graph((n)*(n) + 2);
   }

   // open site (row, col) if it is not open already
   public void open(int i, int j) {
        arr[i][j] = true;

        // Top site.
        if(i == 0) graph.addEdge(n*n, cal(i,j));

        // Bottom site.
        if(i == n-1) graph.addEdge(n*n+1, cal(i,j));

        // bottom site
        if (i < n-1 && arr[i+1][j] == true)
            graph.addEdge(cal(i, j), cal(i+1, j));

        // top site
        if (i > 0   && arr[i-1][j] == true)
            graph.addEdge(cal(i, j), cal(i-1, j));

        // right site
        if (j < n-1 && arr[i][j+1] == true)
            graph.addEdge(cal(i, j), cal(i, j+1));

        // left site
        if (j > 0   && arr[i][j-1] == true)
            graph.addEdge(cal(i, j), cal(i, j-1));
   }

   public int cal(int i, int j) {
        return (n*i)+j;
   }

   // does the system Percolate?
   public boolean percolates() {
        CC cc = new CC(graph);
        if (cc.connected(n*n, n*n+1))
            return true;
        return false;
    }
}


public class Solution {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int size = Integer.parseInt(scan.nextLine());
        Percolate percolate = new Percolate(size);

        while(scan.hasNext()) {
            String[] tokens = scan.nextLine().split(" ");
            percolate.open(Integer.parseInt(tokens[0])-1, 
                Integer.parseInt(tokens[1])-1);
        }

        System.out.println(percolate.percolates());
   }
}