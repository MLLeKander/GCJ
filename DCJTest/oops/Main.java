import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Main {
  public static final PrintStream outWriter = System.out;
  public static class Node {
    int ndx;
    public Node(int ndx_) { ndx=ndx_; }
    public Node put(int i) { message.PutInt(ndx, i); return this; } public void putln(int i) { message.PutInt(ndx, i); send(); }
    public Node put(char i) { message.PutChar(ndx, i); return this; } public void putln(char i) { message.PutChar(ndx, i); send(); }
    public Node put(long i) { message.PutLL(ndx, i); return this; } public void putln(long i) { message.PutLL(ndx, i); send(); }
    public Node put(Puttable i) { i.putTo(this); return this; } public void putln(Puttable i) { i.putTo(this); send(); }
    public int getInt() { return message.GetInt(ndx); } public int getlnInt() { receive(); return message.GetInt(ndx); }
    public long getLong() { return message.GetLL(ndx); } public long getlnLong() { receive(); return message.GetLL(ndx); }
    public char getChar() { return message.GetChar(ndx); } public char getlnChar() { receive(); return message.GetChar(ndx); }
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
  public static Node masterNode = nodes[0];
  static int nodeId = message.MyNodeId();
  public static long[] linspace(long hi, int n) {
    long[] out = new long[n];
    for (int lo = 0, i = 0; i < out.length; i++) {
      lo += (hi-lo)/n--;
      out[i] = lo;
    }
    return out;
  }
  public static long n = oops.GetN();
  public static void slave(long lo, long hi) {
    long min, max = min = oops.GetNumber(lo);
    for (long i = lo+1; i < hi; i++) {
      long tmp = oops.GetNumber(i);
      min = Math.min(min, tmp);
      max = Math.max(max, tmp);
    }
    masterNode.put(min).putln(max);
  }
  public static void master() {
    long min = nodes[0].getlnLong(), max = nodes[0].getLong();
    for (int i = 1; i < nodes.length; i++) {
      min = Math.min(min, nodes[i].getlnLong());
      max = Math.max(max, nodes[i].getLong());
    }
    outWriter.println(max-min);
  }
  public static void main(String[] args) {
    {
      long[] ranges = linspace(n, nodes.length);
      if (nodeId < ranges.length) {
        long lo = nodeId == 0 ? 0 : ranges[nodeId-1], hi = ranges[nodeId];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
  }
}
