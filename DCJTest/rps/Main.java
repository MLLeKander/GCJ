import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Main {
  public static final PrintStream outWriter = System.out;
  public static class Node {
    int ndx;
    public Node(int ndx_) { ndx=ndx_; }
    public Node put(int i) { message.PutInt(ndx, i); return this; } public void putln(int i) { message.PutInt(ndx, i); send(); } public void put(int[] arr) { put(arr.length); for (int i : arr) { message.PutInt(ndx, i); } } public void putln(int[] arr) { put(arr); send(); }
    public Node put(char i) { message.PutChar(ndx, i); return this; } public void putln(char i) { message.PutChar(ndx, i); send(); } public void put(char[] arr) { put(arr.length); for (char i : arr) { message.PutChar(ndx, i); } } public void putln(char[] arr) { put(arr); send(); }
    public Node put(long i) { message.PutLL(ndx, i); return this; } public void putln(long i) { message.PutLL(ndx, i); send(); } public void put(long[] arr) { put(arr.length); for (long i : arr) { message.PutLL(ndx, i); } } public void putln(long[] arr) { put(arr); send(); }
    public Node put(Puttable i) { i.putTo(this); return this; } public void putln(Puttable i) { i.putTo(this); send(); } public void put(Puttable[] arr) { put(arr.length); for (Puttable i : arr) { i.putTo(this); } } public void putln(Puttable[] arr) { put(arr); send(); }
    public int getInt() { return message.GetInt(ndx); } public int getlnInt() { receive(); return message.GetInt(ndx); } public int[] getIntA() { int[] out = new int[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getInt(); } return out; } public int[] getlnIntA() { receive(); return getIntA(); }
    public long getLong() { return message.GetLL(ndx); } public long getlnLong() { receive(); return message.GetLL(ndx); } public long[] getLongA() { long[] out = new long[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getLong(); } return out; } public long[] getlnLongA() { receive(); return getLongA(); }
    public char getChar() { return message.GetChar(ndx); } public char getlnChar() { receive(); return message.GetChar(ndx); } public char[] getCharA() { char[] out = new char[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getChar(); } return out; } public char[] getlnCharA() { receive(); return getCharA(); }
    public void send() { message.Send(ndx); }
    public void receive() { message.Receive(ndx); }
  }
  public interface Puttable { void putTo(Node o); }
  public static Node[] nodes = new Node[message.NumberOfNodes()];
  static {
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = new Node(i);
    }
  }
  public static int activeNodes = nodes.length;
  public static Node masterNode = nodes[0];
  static int nodeId = message.MyNodeId();
  public static long[] linspace(long hi, int n) { return linspace(0, hi, n); }
  public static long[] linspace(long lo_, long hi, int n) {
    n = Math.min((int)(hi-lo_),n);
    long[] out = new long[n+1];
    out[0] = lo_;
    for (long lo = lo_, i = 1; i < out.length; i++) {
      lo += (hi-lo)/n--;
      out[(int)i] = lo;
    }
    return out;
  }
  public static class Player implements Puttable {
    public long id;
    public char c;
    public long id() { return id; }
    public char c() { return c; }
    Player(long id_, char c_) { id=id_; c=c_; }
    Player(Node n) {
      this(n.getLong(),n.getChar());
    }
    public void putTo(Node n) {
      n.put(id);
      n.put(c);
    }
    public static Player get(Node n) { return new Player(n); }
    public static Player getln(Node n) { n.receive(); return get(n); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Player o = (Player)oo;
      return id==o.id&&c==o.c;
    }
    public int hashCode() { return Objects.hash(id,c); }
    public String toString() { return ""+id+","+c; }
  }
  public static Player winner(Player a, Player b) {
    return a.c == b.c || a.c == 'R' && b.c == 'S' || a.c == 'P' && b.c == 'R' || a.c == 'S' && b.c == 'P' ? a : b;
  }
  public static Player simulate(Player[] board) {
    for (int i = board.length; i > 1; i /= 2) {
      for (int j = 0; j < i; j += 2) {
        board[j/2] = winner(board[j], board[j+1]);
      }
    }
    return board[0];
  }
  public static int n = (int)rps.GetN();
  public static long players = 1L << n;
  public static void slave(long lo, long hi) {
    Player[] ps = new Player[(int)(hi-lo)];
    for (int i = 0; i < ps.length; i++) { ps[i] = new Player(i+lo, rps.GetFavoriteMove(i+lo)); }
    nodes[0].putln(simulate(ps));
  }
  public static void master() {
    Player[] tmp = new Player[activeNodes];
    for (int i = 0; i < tmp.length; i++) { tmp[i] = Player.getln(nodes[i]); }
    outWriter.println(simulate(tmp).id);
  }
  public static void main(String[] args) {
    int depth = 1;
    for (; depth <= n && 1 << depth <= nodes.length; depth++) ;
    activeNodes = 1 << depth-1;
    Node[] tmpNodes = new Node[activeNodes];
    for (int i = 0; i < tmpNodes.length; i++) { tmpNodes[i] = nodes[i]; }
    nodes = tmpNodes;
    {
      long[] ranges = linspace(players, nodes.length);
      activeNodes = ranges.length-1;
      if (nodeId+1 < ranges.length) {
        long lo = ranges[nodeId], hi = ranges[nodeId+1];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
  }
}
