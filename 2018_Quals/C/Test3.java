import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Test3 {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static final int ROWS = 20, COLS = 10;
  public static int t = 0, queries = 0;
  public static void main() {
    t = nextInt();
    while (t-- > 0) {
      queries = 0;
      solve();
      System.err.println(queries);
    }
  }
  public static void fillCounts(int[][] counts, int r, int c) {
    for (int dr = -1; dr <= 1; dr++) {
      int r_ = r+dr;
      if (r_ < 1 || r_ >= ROWS-1) {
        continue;
      }
      for (int dc = -1; dc <= 1; dc++) {
        int c_ = c+dc;
        if (c_ < 1 || c_ >= COLS-1) {
          continue;
        }
        counts[r_][c_]++;
      }
    }
  }
  public static void query(int r, int c) {
    queries++;
    outWriter.printf("%d %d\n", r, c);
    outWriter.flush();
  }
  public static void solve() {
    int a = nextInt();
    boolean[][] board = new boolean[ROWS][COLS];
    int[][] counts = new int[ROWS][COLS];
    int queries = 0;
    while (true) {
      int min = Integer.MAX_VALUE, minR = -1, minC = -1;
      for (int r = 1; r < ROWS-1; r++) {
        for (int c = 1; c < COLS-1; c++) {
          if (counts[r][c] < min) {
            min = counts[r][c];
            minR = r;
            minC = c;
          }
        }
      }
      query(minR+1, minC+1);
      int r_ = nextInt(), c_ = nextInt();
      if (r_ == 0 && c_ == 0) {
        return;
      } else if (r_ == -1 && c_ == -1) {
        t = 0;
        return;
      }
      if (!board[r_-1][c_-1]) {
        board[r_-1][c_-1] = true;
        fillCounts(counts, r_-1, c_-1);
      }
    }
  }
  public static void main(String[] args) { main(); outWriter.flush(); }
  public static StringTokenizer tokenizer = null;
  public static String nextLine() {
    try { return buffer.readLine(); } catch (IOException e) { throw new UncheckedIOException(e); }
  }
  public static String next() {
    while (tokenizer == null || !tokenizer.hasMoreElements()) { tokenizer = new StringTokenizer(nextLine()); }
    return tokenizer.nextToken();
  }
  public static int nextInt() { return Integer.parseInt(next()); }
  public static long nextLong() { return Long.parseLong(next()); }
  public static double nextDouble() { return Double.parseDouble(next()); }
}
