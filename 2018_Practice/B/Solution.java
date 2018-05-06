import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Party implements Comparable<Party> {
    public int n;
    public char c;
    public int n() { return n; }
    public char c() { return c; }
    Party(int n_, char c_) { n=n_; c=c_; }
    public static Comparator<Party> cmp = Comparator.comparing(Party::n).thenComparing(Party::c);
    public int compareTo(Party o) { return cmp.compare(this, o); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Party o = (Party)oo;
      return n==o.n&&c==o.c;
    }
    public int hashCode() { return Objects.hash(n,c); }
    public String toString() { return ""+n+","+c; }
  }
  public static Object solve() {
    int N = nextInt();
    PriorityQueue<Party> pq = new PriorityQueue<Party>(Collections.reverseOrder());
    for (int i = 0; i < N; i++) {
      pq.add(new Party(nextInt(), (char)('A'+i)));
    }
    StringBuffer out = new StringBuffer();
    while (pq.size() > 2) {
      Party a = pq.poll();
      out.append(a.c);
      out.append(' ');
      if (--a.n > 0) { pq.add(a); }
    }
    Party a = pq.poll(), b = pq.poll();
    while (a.n-- > 0) {
      out.append(b.c);
      out.append(a.c);
      out.append(' ');
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
