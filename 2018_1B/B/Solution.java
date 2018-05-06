import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Sign {
    public int d;
    public int a;
    public int b;
    public int d() { return d; }
    public int a() { return a; }
    public int b() { return b; }
    Sign(int d_, int a_, int b_) { d=d_; a=a_; b=b_; }
    Sign() {
      this(nextInt(),nextInt(),nextInt());
    }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Sign o = (Sign)oo;
      return o != null && d==o.d&&a==o.a&&b==o.b;
    }
    public int hashCode() { return Objects.hash(d,a,b); }
    public String toString() { return ""+d+","+a+","+b; }
    int m() { return d+a; }
    int n() { return d-b; }
  }
  public static Object solve() {
    Sign[] signs = new Sign[nextInt()];
    for (int i = 0; i < signs.length; i++) {
      signs[i] = new Sign();
    }
    for (Sign sign : signs) {
      System.err.printf("%- 3d  %- 3d\n", sign.m(), sign.n());
    }
    System.err.println();
    return "";
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
