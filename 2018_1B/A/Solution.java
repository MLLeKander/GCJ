import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static int intPart(int n, int d) {
    return (int)(n*100.0/d);
  }
  public static boolean isUp(int n, int d) {
    return (n*100./d)%1 >= 0.5;
  }
  public static Object solve() {
    int n = nextInt(), l = nextInt();
    int[] in = new int[l];
    for (int i = 0; i < in.length; i++) {
      in[i] = nextInt();
    }
    boolean[] isUp = new boolean[n+1];
    for (int i = 0; i < isUp.length; i++) {
      isUp[i] = isUp(i,n);
    }
    int[] tillUp = new int[n+1];
    tillUp[tillUp.length-1] = Integer.MAX_VALUE;
    for (int i = tillUp.length-2; i >= 0; i--) {
      if (!isUp(i,n)) {
        tillUp[i] = tillUp[i+1] + (tillUp[i+1] == Integer.MAX_VALUE ? 0 : 1);
      }
      if (tillUp[i] < Integer.MAX_VALUE && !isUp(i+tillUp[i],n)) {
        throw new RuntimeException();
      }
    }
    int out = 0, used = 0;
    PriorityQueue<Integer> q = new PriorityQueue<Integer>();
    for (int i : in) {
      out += intPart(i,n);
      q.add(tillUp[i]);
      used += i;
    }
    int toSpare = n-used;
    for (int i = 0; i < toSpare; i++) {
      out += intPart(1,n);
      q.add(tillUp[0]);
    }
    while (!q.isEmpty() && toSpare >= q.peek()) {
      toSpare -= q.poll();
      out++;
    }
    return out;
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
