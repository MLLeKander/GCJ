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
  public static long[] votes = new long[0];
  public static long[] majs = new long[0];
  public static long majority(long[] arr) {
    long tmp = Long.MIN_VALUE;
    int cnt = 0;
    for (long l : arr) {
      if (cnt == 0) {
        tmp = l;
      }
      if (l == tmp) {
        cnt++;
      } else {
        cnt--;
      }
    }
    cnt = 0;
    for (long l : arr) {
      if (l == tmp) {
        cnt++;
      }
    }
    return cnt+cnt > arr.length ? tmp : -1;
  }
  public static void slave(long lo, long hi) {
    int diff = (int)(hi-lo);
    votes = new long[diff];
    for (int i = 0; i < diff; i++) {
      votes[i] = majority.GetVote(lo+i);
    }
    masterNode.putln(majority(votes));
  }
  public static void master() {
    majs = new long[nodes.length];
    for (int i = 0; i < majs.length; i++) { majs[i] = nodes[i].getlnLong(); }
    for (Node n : nodes) {
      n.putln(majs);
    }
  }
  public static void slave() {
    long[] majs = masterNode.getlnLongA();
    int[] counts = new int[majs.length];
    for (long l : votes) {
      for (int i = 0; i < nodes.length; i++) {
        if (majs[i] == l) {
          counts[i]++;
          break;
        }
      }
    }
    masterNode.putln(counts);
  }
  public static void master2() {
    int[] counts = new int[nodes.length];
    for (Node n : nodes) {
      int[] tmpCounts = n.getlnIntA();
      for (int i = 0; i < counts.length; i++) {
        counts[i] += tmpCounts[i];
      }
    }
    int max = counts[0];
    int maxNdx = 0;
    for (int i = 0; i < counts.length; i++) {
      if (max < counts[i]) {
        max = counts[i];
        maxNdx = i;
      }
    }
    outWriter.println(max+max > majority.GetN() ? majs[maxNdx] : "NO WINNER");
  }
  public static void main(String[] args) {
    {
      long[] ranges = linspace(majority.GetN(), nodes.length);
      if (nodeId < ranges.length) {
        long lo = nodeId == 0 ? 0 : ranges[nodeId-1], hi = ranges[nodeId];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
    slave();
    if (nodeId == 0) { master2(); }
  }
}
