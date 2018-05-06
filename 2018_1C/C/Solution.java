import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static Object solve() {
    int n = nextInt();
    long[] ws = new long[n];
    for (int i = 0; i < ws.length; i++) {
      ws[i] = nextLong();
    }
    long[][] dp = new long[n][ 200];
    Arrays.fill(dp[0], Long.MAX_VALUE);
    dp[0][0] = 0;
    dp[0][1] = ws[0];
    for (int a = 1; a < dp.length; a++) {
      long currW = ws[a];
      for (int o = 0; o < dp[0].length; o++) {
        dp[a][o] = Long.MAX_VALUE;
        if (o > 0 && currW*6 >= dp[a-1][o-1]) {
          dp[a][o] = Math.min(dp[a][o],currW+dp[a-1][o-1]);
        }
        dp[a][o] = Math.min(dp[a][o],dp[a-1][o]);
      }
    }
    for (int i = 0; i < dp[n-1].length; i++) {
      if (dp[n-1][i] == Long.MAX_VALUE) {
        return i-1;
      }
    }
    return -1;
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
