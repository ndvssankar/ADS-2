
import edu.princeton.cs.algs4.SET;
// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.In;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private int rows;
    private int cols;
    private boolean[][] visited;
    private final TrieST<Integer> dictTST;

    public BoggleSolver(String[] dictionary) {
        dictTST = new TrieST<Integer>();
        int[] scores = {0, 0, 0, 1, 1, 2, 3, 5, 11};
        for (String word : dictionary) {
            if (word.length() < scores.length)
                dictTST.put(word, scores[word.length()]);
            else
                dictTST.put(word, 11);
        }
    }

    private boolean isValidWord(String str) {
        if (str.length() <= 2)
            return false;
        return dictTST.contains(str);
    }

    private void findWords(BoggleBoard board, int i, int j,
        SET<String> queue, String sb) {
        if (!dictTST.hasPrefix(sb)) {
            return;
        }
        if (isValidWord(sb)) {
            queue.add(sb);
        }
        visited[i][j] = true;
        for (int row = i - 1; row <= i + 1 && row < rows; row++) {
            for (int col = j - 1; col <= j + 1 && col < cols; col++) {
                if (row >= 0 && col >= 0 && !visited[row][col]) {
                    String newSB = appendChar(sb, board.getLetter(row, col));
                    findWords(board, row, col, queue, newSB);
                }
            }
        }
        visited[i][j] = false;
    }

    private String appendChar(String sb, char ch) {
        if (ch == 'Q') return sb + "QU";
        return sb + ch;
    }

    private Iterable<String> findAllWords(BoggleBoard board) {

        if (board == null) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }

        rows = board.rows();
        cols = board.cols();

        visited = new boolean[rows][cols];
        SET<String> queue = new SET<String>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                String sb = appendChar("", board.getLetter(row, col));
                findWords(board, row, col, queue, sb);
            }
        }
        return queue;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        return findAllWords(board);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictTST.get(word) == null)
            return 0;
        return dictTST.get(word);
    }

    //  public static void main(String[] args) {
    //  In in = new In(args[0]);
    //  String[] dictionary = in.readAllStrings();
    //  BoggleSolver solver = new BoggleSolver(dictionary);
    //  BoggleBoard board = new BoggleBoard(args[1]);
    //  int score = 0;
    //  for (String word : solver.getAllValidWords(board)) {
    //      StdOut.println(word + " :: " + solver.scoreOf(word));
    //      score += solver.scoreOf(word);
    //  }
    //  StdOut.println("Score = " + score);
    // }
}