import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Cashier {
    public long max;
    public long s;
    public long p;
    public long max() { return max; }
    public long s() { return s; }
    public long p() { return p; }
    Cashier(long max_, long s_, long p_) { max=max_; s=s_; p=p_; }
    Cashier() {
      this(nextLong(),nextLong(),nextLong());
    }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Cashier o = (Cashier)oo;
      return max==o.max&&s==o.s&&p==o.p;
    }
    public int hashCode() { return Objects.hash(max,s,p); }
    public String toString() { return ""+max+","+s+","+p; }
    long bitsAt(long t) { return Math.max(0, Math.min(max, (t-p)/s)); }
    long maxT() { return s*max + p; }
  }
  public static boolean canFit(Cashier[] cs, int r, long b, long t) {
    long[] bts = new long[cs.length];
    for (int i = 0; i < bts.length; i++) { bts[i] = cs[i].bitsAt(t); }
    Arrays.sort(bts);
    long sum = 0;
    for (int c = 0, i = bts.length-1; c < r && i >= 0; i--, c++) {
      sum += bts[i];
    }
    return sum >= b;
  }
  public static Object solve() {
    int r = nextInt();
    long b = nextLong();
    int c = nextInt();
    Cashier[] cs = new Cashier[c];
    for (int i = 0; i < cs.length; i++) { cs[i] = new Cashier(); }
    long lo = 0, hi = Long.MAX_VALUE;
    while (lo < hi) {
      long mid = lo + (hi-lo)/2;
      if (canFit(cs, r, b, mid)) {
        hi = mid;
      } else {
        lo = mid+1;
      }
    }
    return hi;
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
