import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

   private static final double BOUNDARY = 1000;
   private Picture picture;

   public SeamCarver(Picture picture) {
      if (picture == null) {
         throw new IllegalArgumentException("picture is null");
      }
      this.picture = new Picture(picture);
   }

   // current picture
   public Picture picture() {
      return new Picture(picture);
   }

   // width of current picture
   public int width() {
      return this.picture.width();
   }

   // height of current picture
   public int height() {
      return this.picture.height();
   }

   private double[][] calculateEnergies() {
      double[][] energies = new double[height()][width()];
      for (int i = 0; i < this.height(); i++) {
         for (int j = 0; j < this.width(); j++) {
            energies[i][j] = energy(j, i);
         }
      }
      return energies;
   }

   private int getRed(int rgb) {
      return (rgb >> 16) & 0xFF;
   }

   private int getGreen(int rgb) {
      return (rgb >>  8) & 0xFF;
   }

   private int getBlue(int rgb) {
      return (rgb >>  0) & 0xFF;
   }

   private double calculateDelta(int c1, int c2) {
      int r = getRed(c1) - getRed(c2);
      int g = getGreen(c1) - getGreen(c2);
      int b = getBlue(c1) - getBlue(c2);
      return (r * r + g * g + b * b);
   }

   private double calculateDeltaY(int x, int y) {
      int c1 = this.picture.getRGB(x, y - 1);
      int c2 = this.picture.getRGB(x, y + 1);
      return calculateDelta(c1, c2);
   }

   private double calculateDeltaX(int x, int y) {
      int c1 = this.picture.getRGB(x - 1, y);
      int c2 = this.picture.getRGB(x + 1, y);
      return calculateDelta(c1, c2);
   }

   private void validate(int x, int length) {
      if (x < 0 || x >= length) {
         throw new IllegalArgumentException("IllegalArgumentException");
      }
   }

   // energy of pixel at column x and row y
   public double energy(int x, int y) {
      validate(x, this.width());
      validate(y, this.height());
      if (x == 0 || y == 0 || x == this.width()-1 || y == this.height()-1) {
         return BOUNDARY;
      } else {
         double deltaX = calculateDeltaX(x, y);
         double deltaY = calculateDeltaY(x, y);
         return Math.sqrt(deltaX + deltaY);
      }
   }

    // pass through an array and mark the shorthest distance from top to entry
    private void topologicalSort(double[][] energies) {
        int h = energies.length, w = energies[0].length;
        for (int row = 1; row < h; row++) {
            for (int col = 0; col < w; col++) {
                double temp = energies[row - 1][col];
                double min = 0;
                if (col == 0) {
                    min = temp;
                } else {
                    min = Math.min(temp, energies[row - 1][col - 1]);
                }

                if (col != (w - 1)) {
                    min = Math.min(min, energies[row - 1][col + 1]);
                }
                energies[row][col] += min;
            }
        }
        StdOut.println("");
        for (int row = 0; row < h; row++) {
          for (int col = 0; col < w; col++) {
            StdOut.printf("%9.2f ", energies[row][col]);
          }
          StdOut.println("");
        }
    }

    private int[] minVerticalPath(double[][] energies) {
        int h = energies.length;
        int w = energies[0].length;
        int[] path = new int[h];

        topologicalSort(energies);

        path[h - 1] = 0;
        for (int i = 0; i < w; i++) {
            if (energies[h - 1][i] < energies[h - 1][path[h - 1]])
                path[h - 1] = i;
        }

        for (int row = h - 2; row >= 0; row--) {
            int col = path[row + 1];
            path[row] = col;
            if (col > 0 && energies[row][col - 1] < energies[row][path[row]])
                path[row] = col - 1;
            if (col < (w - 2) && energies[row][col + 1] < energies[row][path[row]])
                path[row] = col + 1;
        }
        return path;
    }   

   // sequence of indices for vertical seam
   public int[] findVerticalSeam() {
      return minVerticalPath(calculateEnergies());
   }

   // sequence of indices for horizontal seam
   public int[] findHorizontalSeam() {
      return minVerticalPath(transpose(calculateEnergies()));
   }

   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] seam) {
      if (!validateSeam(seam, this.width(), this.height())) {
         throw new IllegalArgumentException("IllegalArgumentException");
      }
      Picture pic = new Picture(width(), height()-1);
      for (int w = 0; w < width(); w++) {
        for (int h = 0; h < seam[w]; h++) {
          pic.set(w, h, this.picture.get(w, h));
        }

        for (int h = seam[w] + 1; h < height(); h++) {
          pic.set(w, h - 1, this.picture.get(w, h));
        }
      }
      this.picture = pic;
   }

   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam) {
      if (!validateSeam(seam, this.height(), this.width())) {
          throw new IllegalArgumentException("IllegalArgumentException");
      }
      Picture pic = new Picture(this.width()-1, this.height());
      for (int h = 0; h < this.height(); h++) {
        for (int w = 0; w < seam[h]; w++) {
          pic.set(w, h, this.picture.get(w, h));
        }
        for (int w = seam[h] + 1; w < this.width(); w++) {
          pic.set(w-1, h, this.picture.get(w, h));
        }
      }
      this.picture = pic;
   }

   private boolean validateSeam(int[] seam, int length, int range) {
      if (seam == null) {
         return false;
      }
      if (seam.length < length || seam[0] < 0 || seam[0] > range) {
         return false;
      }
      for (int i = 1; i < length; i++) {
         if (seam[i] < Math.max(0, seam[i - 1] - 1) || seam[i] > Math.min(range, seam[i - 1] + 1))
            return false;
      }
      return true;
   }

   private double[][] transpose(double[][] energies) {
      double[][] res = new double[this.width()][this.height()];
      for (int x = 0; x < this.height(); x++) {
         for (int y = 0; y < this.width(); y++) {
            res[y][x] = energies[x][y];
         }
      }
      return res;
   }
}