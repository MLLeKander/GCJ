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
  public static long hi = query_of_death.GetLength(), lo = 0;
  public static long runningSum = 0;
  public static void slave(long lo, long hi) {
    int range = (int)(hi-lo);
    long sum = 0;
    if (range == 1) {
      sum = query_of_death.GetValue(lo);
    } else if (range > 1) {
      for (int i = 0; i < range; i++) {
        long tmp = query_of_death.GetValue(i+lo);
        if (tmp != query_of_death.GetValue(i+lo) || tmp != query_of_death.GetValue(i+lo) || tmp != query_of_death.GetValue(i+lo)) {
          sum = -1;
          break;
        }
        sum += tmp;
      }
    }
    masterNode.putln(sum);
  }
  public static void deleteNode(int i) {
    Node[] tmp = new Node[nodes.length-1];
    for (int newNdx = 0, oldNdx = 0; newNdx < tmp.length; oldNdx++) {
      if (oldNdx != i) {
        tmp[newNdx++] = nodes[oldNdx];
      }
    }
    nodes = tmp;
  }
  public static boolean master() {
    int badNdx = -1;
    long[] resps = new long[nodes.length];
    for (int i = 0; i < resps.length; i++) { resps[i] = nodes[i].getlnLong(); }
    for (int i = 0; i < nodes.length; i++) {
      long tmp = resps[i];
      if (tmp == -1) {
        badNdx = i;
        continue;
      }
      runningSum += tmp;
    }
    for (Node n : nodes) {
      n.putln(badNdx);
    }
    if (badNdx == -1) {
      outWriter.println(runningSum);
      return true;
    }
    deleteNode(badNdx);
    return false;
  }
  public static void main(String[] args) {
    deleteNode(0);
    nodeId--;
    while (true) {
      if (nodeId >= 0) {
        long[] ranges = linspace(lo, hi, nodes.length);
        if (nodeId+1 < ranges.length) {
          slave(ranges[nodeId], ranges[nodeId+1]);
        } else {
          masterNode.putln(0l);
        }
        int badNode = masterNode.getlnInt();
        if (badNode == -1 || badNode == nodeId) {
          return;
        } else if (badNode < nodeId) {
          nodeId--;
        }
        deleteNode(badNode);
        lo = ranges[badNode];
        hi = ranges[badNode+1];
      } else {
        if (master()) {
          return;
        }
      }
    }
  }
}
