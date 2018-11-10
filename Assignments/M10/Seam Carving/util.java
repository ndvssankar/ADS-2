
public class util {

   public static int[][] transpose(int[][] energies, int w, int h) {
      int[][] res = new int[h][w];
      for (int x = 0; x < h; x++) {
         System.arraycopy(energies[x], 0, res[x], 0, w);
         // for (int y = 0; y < this.width(); y++) {
         //    res[y][x] = energies[x][y];
         // }
      }
      System.out.println(java.util.Arrays.deepToString(res));
      return res;
   }

   public static void main(String[] args) {
      int a[][] = { {1, 2, 3, 4}, {5, 6, 7, 8}};
      transpose(a, 2, 4);
   }
}

   