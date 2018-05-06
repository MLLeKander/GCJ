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
  public static long minSub(long[] arr) {
    long min = 0, currMin = 0;
    for (long i : arr) {
      currMin += i;
      if (currMin > 0) {
        currMin = 0;
      }
      if (min > currMin) {
        min = currMin;
      }
    }
    return min;
  }
  public static void slave(long lo, long hi) {
    long[] arr = new long[(int)(hi-lo)];
    for (int i = 0; i < arr.length; i++) { arr[i] = sandwich.GetTaste(i+lo); }
    long sum = 0, maxL = 0, maxR = 0, maxM = 0, tmp;
    for (int i = 0; i < arr.length; i++) {
      sum += arr[i];
    }
    tmp = 0;
    for (int i = 0; i < arr.length; i++) {
      tmp += arr[i];
      maxL = Math.max(maxL, tmp);
    }
    tmp = 0;
    for (int i = arr.length-1; i >= 0; i--) {
      tmp += arr[i];
      maxR = Math.max(maxR, tmp);
    }
    maxM = sum - minSub(arr);
    masterNode.put(sum).put(maxL).put(maxR).putln(maxM);
  }
  public static void master() {
    long total = 0;
    long[] sums = new long[nodes.length];
    for (int i = 0; i < sums.length; i++) { sums[i] = nodes[i].getlnLong(); total += sums[i]; }
    long[] maxL = new long[nodes.length];
    for (int i = 0; i < maxL.length; i++) { maxL[i] = nodes[i].getLong(); }
    long[] maxR = new long[nodes.length];
    for (int i = 0; i < maxR.length; i++) { maxR[i] = nodes[i].getLong(); }
    long[] maxM = new long[nodes.length];
    for (int i = 0; i < maxM.length; i++) { maxM[i] = nodes[i].getLong(); }
    long max = Math.max(0, total);
    for (int i = 0; i < nodes.length; i++) {
      max = Math.max(max,total - sums[i] + maxM[i]);
    }
    for (int l = 0, lEaten = 0; l < nodes.length; lEaten += sums[l++]) {
      for (int r = nodes.length-1, rEaten = 0; r > l; rEaten += sums[r--]) {
        max = Math.max(max,lEaten + maxL[l] + rEaten + maxR[r]);
      }
    }
    outWriter.println(max);
  }
  public static void main(String[] args) {
    {
      long[] ranges = linspace(sandwich.GetN(), nodes.length);
      if (nodeId < ranges.length) {
        long lo = nodeId == 0 ? 0 : ranges[nodeId-1], hi = ranges[nodeId];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
  }
}
