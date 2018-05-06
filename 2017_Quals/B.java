import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class B {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static Object solve() {
    char[] s = nextLine().toCharArray();
    int ndx = -1;
    for (int i = 0; i < s.length-1 && ndx == -1; i++) {
      if (s[i+1] < s[i]) {
        ndx = i;
      }
    }
    if (ndx != -1) {
      char tmp = s[ndx];
      while (ndx > 0 && s[ndx-1] == tmp) {
        ndx--;
      }
      s[ndx]--;
      for (int i = ndx+1; i < s.length; i++) {
        s[i] = '9';
      }
    }
    String out = new String(s);
    return out.charAt(0) == '0' ? out.substring(1) : out;
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
