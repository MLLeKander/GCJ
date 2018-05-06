import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static int[] slice(int[] counts, int N, int slices) {
    int[] out = new int[slices+2];
    int sum = 0, expected = N/(slices+1);
    int oNdx = 1;
    for (int cNdx = 0; cNdx < counts.length && oNdx < out.length; cNdx++) {
      sum += counts[cNdx];
      if (counts[cNdx] != 0 && sum%expected == 0) {
        out[oNdx++] = cNdx+1;
      }
    }
    return oNdx == out.length ? out : null;
  }
  public static int countSlice(boolean[][] board, int rMin, int rMax, int cMin, int cMax) {
    int out = 0;
    for (int r = rMin; r < rMax; r++) {
      for (int c = cMin; c < cMax; c++) {
        if (board[r][c]) {
          out++;
        }
      }
    }
    return out;
  }
  public static Object solve() {
    int r = nextInt(), c = nextInt(), h = nextInt(), v = nextInt();
    int slices = (h+1)*(v+1);
    boolean[][] board = new boolean[r][c];
    int[] rCounts = new int[r], cCounts = new int[c];
    int N = 0;
    for (int r_ = 0; r_ < r; r_++) {
      char[] l = nextLine().toCharArray();
      for (int c_ = 0; c_ < c; c_++) {
        if (board[r_][c_] = (l[c_] == '@')) {
          rCounts[r_]++;
          cCounts[c_]++;
          N++;
        }
      }
    }
    if (N % slices != 0) {
      return "IMPOSSIBLE";
    } else if (N == 0) {
      return "POSSIBLE";
    }
    int expected = N / slices;
    int[] rSlices = slice(rCounts, N, h);
    int[] cSlices = slice(cCounts, N, v);
    if (rSlices == null || cSlices == null) {
      return "IMPOSSIBLE";
    }
    for (int r_ = 0; r_ < h; r_++) {
      for (int c_ = 0; c_ < v; c_++) {
        if (countSlice(board, rSlices[r_], rSlices[r_+1], cSlices[c_], cSlices[c_+1]) != expected) {
          return "IMPOSSIBLE";
        }
      }
    }
    return "POSSIBLE";
  }
  public static void main(String[] args) {
    int T = nextInt();
    for (int i = 0; i < T; i++) {
      outWriter.print("Case #"+(i+1)+": ");
      Object tmp = solve();
      if (tmp != null) { outWriter.println(tmp); }
    }
    outWriter.flush();
  }
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
