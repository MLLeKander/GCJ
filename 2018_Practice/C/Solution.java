import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Horse {
    public int x;
    public int s;
    public int d;
    public int x() { return x; }
    public int s() { return s; }
    public int d() { return d; }
    Horse(int x_, int s_, int d_) { x=x_; s=s_; d=d_; }
    Horse() {
      this(nextInt(),nextInt(),nextInt());
    }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Horse o = (Horse)oo;
      return x==o.x&&s==o.s&&d==o.d;
    }
    public int hashCode() { return Objects.hash(x,s,d); }
    public String toString() { return ""+x+","+s+","+d; }
    double arrivalTime() {
      int dist = d-x;
      return dist/(double)s;
    }
  }
  public static Object solve() {
    int d = nextInt(), n = nextInt();
    Horse[] horses = new Horse[n];
    for (int i = 0; i < horses.length; i++) { horses[i] = new Horse(nextInt(), nextInt(), d); }
    double max = 0;
    for (Horse h : horses) {
      max = Math.max(max,h.arrivalTime());
    }
    return d/max;
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
