import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Test {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static void main() {
    ArrayList<Integer> a = new ArrayList<Integer>();
    for (int i = 0; i < 10; i++) {
      a.add(i);
    }
    Collections.shuffle(a.subList(5,10));
    System.err.println("a:"+(a));
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
