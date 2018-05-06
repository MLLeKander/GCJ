import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class B {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Horse implements Comparable<Horse> {
    public int cnt;
    public char rep;
    public int cnt() { return cnt; }
    public char rep() { return rep; }
    Horse(int cnt_, char rep_) { cnt=cnt_; rep=rep_; }
    public Horse clone() { return new Horse(cnt,rep); }
    public static Comparator<Horse> cmp = Comparator.comparing(Horse::cnt).thenComparing(Horse::rep);
    public int compareTo(Horse o) { return cmp.compare(this, o); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Horse o = (Horse)oo;
      return cnt==o.cnt&&rep==o.rep;
    }
    public int hashCode() { return Objects.hash(cnt,rep); }
    public String toString() { return ""+cnt+","+rep; }
  }
  public static boolean az(int... in) {
    for (int i : in) {
      if (i != 0) {
        return false;
      }
    }
    return true;
  }
  public static void add(StringBuilder out, Horse tmp) {
    out.append(tmp.rep);
    tmp.cnt--;
  }
  public static void add2(StringBuilder out, Horse a, Horse b, Horse c) {
    Horse[] h = new Horse[]{a,b,c};
    Arrays.sort(h);
    a = h[0]; b = h[1]; c = h[2];
    while (c.cnt > b.cnt && a.cnt > 0) {
      add(out, c);
      add(out, b);
      if (c.cnt > 0 && a.cnt > 0) {
        add(out, c);
        add(out, a);
      }
    }
    if (c.cnt > b.cnt) { throw new Asdf(); }
    while (c.cnt > 0) {
      add(out, c);
      add(out, b);
      if (a.cnt > 0) {
        add(out, a);
      }
    }
  }
  public static class Asdf extends RuntimeException {
  }
  public static boolean okay(int pure, int mix) { return mix == 0 || mix+1 <= pure; }
  public static String glue(char a, char b, int n) {
    String out = a+"";
    for (int i = 0; i < n; i++) {
      out += b;
      out += a;
    }
    return out;
  }
  public static String rep(String s, char a, char b, int n) {
    return s.replace(a+"",glue(a,b,n));
  }
  public static Object solve() {
    int n = nextInt(), r = nextInt(), o = nextInt(), y = nextInt(), g = nextInt(), b = nextInt(), v = nextInt();
    StringBuilder out = new StringBuilder();
    if (az(v,y,o,b) && g == r) { return glue('R','G',g).substring(1); }
    if (az(g,r,o,b) && v == y) { return glue('Y','V',v).substring(1); }
    if (az(v,y,o,b) && o == b) { return glue('B','O',o).substring(1); }
    if (!okay(b, o) || !okay(y, v) || !okay(r,g)) {
      return "IMPOSSIBLE";
    }
    b -= o; r -= g; y -= v;
    try {
      add2(out, new Horse(r, 'R'), new Horse(y, 'Y'), new Horse(b, 'B'));
    } catch (Asdf a) {
      return "IMPOSSIBLE";
    }
    String s = out.toString();
    s = rep(s, 'R', 'G', g);
    s = rep(s, 'Y', 'V', v);
    s = rep(s, 'B', 'O', o);
    return s;
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
