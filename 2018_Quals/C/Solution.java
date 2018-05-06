import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static int t;
  public static void main() {
    t = nextInt();
    while (t-- > 0) {
      solve();
    }
  }
  public static boolean allTrue(boolean[] a) {
    for (boolean b : a) {
      if (!b) {
        return false;
      }
    }
    return true;
  }
  public static void solve() {
    int a = nextInt();
    boolean[][] board = new boolean[67][3];
    outWriter.println("66 2"); outWriter.flush(); nextInt(); nextInt();
    int r = 2;
    while (true) {
      outWriter.printf("%d %d\n", r, 2);
      outWriter.flush();
      int r_ = nextInt(), c_ = nextInt();
      if (r_ == 0 && c_ == 0) {
        return;
      } else if (r_ == -1 && c_ == -1) {
        t = 0;
        return;
      }
      board[r_-1][c_-1] = true;
      while (r < board.length-1 && allTrue(board[r-2])) {
        r++;
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
