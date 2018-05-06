import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class test {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static void main() {
    long min = nextLong();
    long max = nextLong();
    long maxVal = 1;
    for (int i = 1; i < max+1; i++) {
      if (isIncreasing(i)) {
        maxVal = i;
      }
      if (i >= min) {
        Object tmp = solveTmp(i);
        if (!(tmp+"").equals(maxVal+"")) {
          System.err.println("i:"+(i)+"  tmp:"+(tmp)+"  maxVal:"+(maxVal));
          return;
        }
      }
    }
  }
  public static boolean isIncreasing(long i) {
    while (i >= 10) {
      if (i%10 < (i /= 10)%10) {
        return false;
      }
    }
    return true;
  }
  public static Object solveTmp(Object in) {
    char[] s = in.toString().toCharArray();
    int ndx = -1;
    for (int i = 0; i < s.length-1 && ndx == -1; i++) {
      if (s[i+1] < s[i]) {
        ndx = i;
      }
    }
    if (ndx == -1) {
      return in;
    }
    char tmp = s[ndx];
    while (ndx > 0 && s[ndx-1] == tmp) {
      ndx--;
    }
    s[ndx]--;
    for (int i = ndx+1; i < s.length; i++) {
      s[i] = '9';
    }
    String out = new String(s);
    return out.charAt(0) == '0' ? out.substring(1) : out;
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
