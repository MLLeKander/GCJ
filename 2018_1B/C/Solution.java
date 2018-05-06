import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Recipie {
    public int a;
    public int b;
    public int a() { return a; }
    public int b() { return b; }
    Recipie(int a_, int b_) { a=a_; b=b_; }
    Recipie() {
      this(nextInt(),nextInt());
    }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Recipie o = (Recipie)oo;
      return o != null && a==o.a&&b==o.b;
    }
    public int hashCode() { return Objects.hash(a,b); }
    public String toString() { return ""+a+","+b; }
  }
  public static boolean anyNeg(long[] s) {
    for (long tmp : s) {
      if (tmp < 0) {
        return true;
      }
    }
    return false;
  }
  public static boolean canFit(long l, long sum, long[] start, Recipie[] rs) {
    long[] curr = Arrays.copyOf(start, start.length);
    curr[0] -= l;
    boolean flag = true;
    while (sum > 0 && flag) {
      flag = false;
      for (int i = 0; i < curr.length; i++) {
        if (curr[i] < 0) {
          Recipie r = rs[i];
          if (r.a == i || r.b == i) { return false; }
          sum += curr[i];
          curr[r.a] += curr[i];
          curr[r.b] += curr[i];
          curr[i] = 0;
          flag = true;
        }
      }
    }
    return !anyNeg(curr);
  }
  public static Object solve() {
    int m = nextInt();
    Recipie[] rs = new Recipie[m];
    for (int i = 0; i < rs.length; i++) {
      rs[i] = new Recipie(nextInt()-1, nextInt()-1);
    }
    long[] start = new long[m];
    for (int i = 0; i < start.length; i++) {
      start[i] = nextLong();
    }
    long sum = 0;
    for (long s : start) {
      sum += s;
    }
    long lo = 0, hi = sum+1;
    while (lo+1 < hi) {
      long mid = (lo+hi)/2;
      boolean c = canFit(mid, sum, start, rs);
      if (c) {
        lo = mid;
      } else {
        hi = mid;
      }
    }
    return lo;
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
