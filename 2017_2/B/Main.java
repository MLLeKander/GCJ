import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Main {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static Object solve() {
    int n = nextInt(), c = nextInt(), m = nextInt();
    int[] positions = new int[m], customers = new int[m];
    for (int i = 0; i < m; i++) {
      positions[i] = nextInt();
      customers[i] = nextInt();
    }
    System.err.println("ats(positions):"+(Arrays.toString(positions)));
    System.err.println("ats(customers):"+(Arrays.toString(customers)));
    return null;
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
