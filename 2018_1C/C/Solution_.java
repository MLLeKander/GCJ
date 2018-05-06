import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution_ {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static Object solve() {
    int n = nextInt();
    long[] ws = new long[n];
    for (int i = 0; i < ws.length; i++) {
      ws[i] = nextLong();
    }
    ArrayList<Long> dp = new ArrayList<Long>();
    dp.add(0L);
    for (long w : ws) {
      long prevEnd = dp.get(dp.size()-1);
      for (int o = dp.size()-1; o > 0; o--) {
        long tmp = w + dp.get(o-1);
        if (6*w >= dp.get(o-1) && tmp < dp.get(o)) {
          dp.set(o, tmp);
        }
      }
      if (w*6 >= prevEnd) {
        dp.add(w + prevEnd);
      }
    }
    return dp.size()-1;
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
