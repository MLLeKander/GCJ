import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class C {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static class Horse implements Comparable<Horse> {
    public long maxDist;
    public double speed;
    public long maxDist() { return maxDist; }
    public double speed() { return speed; }
    Horse(long maxDist_, double speed_) { maxDist=maxDist_; speed=speed_; }
    public Horse clone() { return new Horse(maxDist,speed); }
    public static Comparator<Horse> cmp = Comparator.comparing(Horse::maxDist).thenComparing(Horse::speed);
    public int compareTo(Horse o) { return cmp.compare(this, o); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Horse o = (Horse)oo;
      return maxDist==o.maxDist&&speed==o.speed;
    }
    public int hashCode() { return Objects.hash(maxDist,speed); }
    public String toString() { return ""+maxDist+","+speed; }
  }
  public static class Query implements Comparable<Query> {
    public int start;
    public int end;
    public int start() { return start; }
    public int end() { return end; }
    Query(int start_, int end_) { start=start_; end=end_; }
    public Query clone() { return new Query(start,end); }
    public static Comparator<Query> cmp = Comparator.comparing(Query::start).thenComparing(Query::end);
    public int compareTo(Query o) { return cmp.compare(this, o); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Query o = (Query)oo;
      return start==o.start&&end==o.end;
    }
    public int hashCode() { return Objects.hash(start,end); }
    public String toString() { return ""+start+","+end; }
  }
  public static class Adj {
    public long cost;
    public Node target;
    public long cost() { return cost; }
    public Node target() { return target; }
    Adj(long cost_, Node target_) { cost=cost_; target=target_; }
    public String toString() { return String.format("%d(%d)", target.i, cost); }
  }
  public static class Node {
    public int i;
    public int i() { return i; }
    Node(int i_) { i=i_; }
    boolean visited = false;
    double minTime = Double.MAX_VALUE;
    double minTime() { return minTime; }
    public ArrayList<Adj> adj = new ArrayList<Adj>();
    public String toString() { return i+":"+adj; }
  }
  public static void solve(Node[] nodes, int start, int end) {
    for (Node n : nodes) { n.visited = false; n.minTime = Double.MAX_VALUE; }
  }
  public static Object solve() {
    int n = nextInt(), q = nextInt();
    Horse[] horses = new Horse[n];
    for (int i = 0; i < horses.length; i++) { horses[i] = new Horse(nextLong(), nextDouble()); }
    Node[] nodes = new Node[n];
    for (int i = 0; i < nodes.length; i++) { nodes[i] = new Node(i); }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        long dist = nextLong();
        if (dist != -1) {
          nodes[i].adj.add(new Adj(dist, nodes[j]));
        }
      }
    }
    System.err.println("ats(nodes):"+(Arrays.toString(nodes)));
    for (int i = 0; i < q; i++) { solve(nodes, nextInt(), nextInt()); }
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
