import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Test {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static long[] b = new long[]{3,5,7,9,11,13};
  public static long getB(long i) { return b[(int)i]; }
  public static long biSearchB(long target) {
    long lo = 0, hi = b.length-1;
    while (lo < hi) {
      long mid = (lo+hi)/2;
      if (getB(mid) >= target) {
        hi = mid;
      } else {
        lo = mid+1;
      }
    }
    return getB(lo) >= target ? lo : -1;
  }
  public static void main() {
    for (int i = 0; i < 15; i++) {
      long q = i, ndx = biSearchB(q), b = ndx == -1 ? -1 : getB(ndx);
      System.err.println("q:"+(q)+"  ndx:"+(ndx)+"  b:"+(b));
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
