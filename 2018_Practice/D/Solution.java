import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static void addPut(Map<Long,Long> map, Long k, Long v) {
    if (map.containsKey(k)) {
      v += map.get(k);
    }
    map.put(k,v);
  }
  public static Object solve() {
    long n = nextLong(), m = nextLong();
    TreeMap<Long,Long> map = new TreeMap<Long,Long>();
    map.put(n,1L);
    long curr = 0;
    while (!map.isEmpty()) {
      Map.Entry<Long,Long> e = map.pollLastEntry();
      long k = e.getKey(), v = e.getValue();
      if (k == 0) {
        continue;
      }
      curr += v;
      long mn = (k-1)/2, mx = k/2;
      if (curr >= m) {
        return mx+" "+mn;
      }
      addPut(map, mx, v);
      addPut(map, mn, v);
    }
    throw new RuntimeException();
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
