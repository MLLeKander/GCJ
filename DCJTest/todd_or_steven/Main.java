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
  public static long oLen = todd_and_steven.GetToddLength();
  public static long eLen = todd_and_steven.GetStevenLength();
  public static long PRIME = 1000000007l;
  public static long oBiSearch(long target) {
    long lo = 0, hi = oLen-1;
    while (lo < hi) {
      long mid = (lo+hi)/2;
      if (todd_and_steven.GetToddValue(mid) >= target) {
        hi = mid;
      } else {
        lo = mid+1;
      }
    }
    return todd_and_steven.GetToddValue(lo) >= target ? lo : oLen;
  }
  public static long eBiSearch(long target) {
    long lo = 0, hi = eLen-1;
    while (lo < hi) {
      long mid = (lo+hi)/2;
      if (todd_and_steven.GetStevenValue(mid) >= target) {
        hi = mid;
      } else {
        lo = mid+1;
      }
    }
    return todd_and_steven.GetStevenValue(lo) >= target ? lo : eLen;
  }
  public static void slave(long lo, long hi) {
    long oNdx = oBiSearch(lo), eNdx = eBiSearch(lo);
    long o = oNdx == oLen ? Long.MAX_VALUE : todd_and_steven.GetToddValue(oNdx), e = eNdx == eLen ? Long.MAX_VALUE : todd_and_steven.GetStevenValue(eNdx);
    long ndx = oNdx + eNdx, out = 0;
    for (; Math.min(o, e) < hi; ndx++) {
      if (o < e) {
        out = (out + (o ^ ndx))%PRIME;
        o = (++oNdx >= oLen) ? Long.MAX_VALUE : todd_and_steven.GetToddValue(oNdx);
      } else {
        out = (out + (e ^ ndx))%PRIME;
        e = (++eNdx >= eLen) ? Long.MAX_VALUE : todd_and_steven.GetStevenValue(eNdx);
      }
    }
    masterNode.putln(out);
  }
  public static void main(String[] args) {
    int n = (nodes.length)/2;
    long[] oRanges = linspace(oLen, n), eRanges = linspace(eLen, n);
    if (nodeId == 0) { System.err.println("ats(oRanges):"+(Arrays.toString(oRanges))+"  ats(eRanges):"+(Arrays.toString(eRanges))); }
    long[] ranges = new long[oRanges.length + eRanges.length - 1];
    for (int i = 0; i < oRanges.length-1; i++) {
      ranges[i] = todd_and_steven.GetToddValue(oRanges[i]);
    }
    for (int i = 0; i < eRanges.length-1; i++) {
      ranges[i+oRanges.length-1] = todd_and_steven.GetStevenValue(eRanges[i]);
    }
    ranges[ranges.length-1] = Integer.MAX_VALUE;
    Arrays.sort(ranges);
    if (nodeId == 0) { System.err.println("ats(ranges):"+(Arrays.toString(ranges))); }
    {
      activeNodes = ranges.length-1;
      if (activeNodes > nodes.length) { System.err.println("ERR"); }
      if (nodeId+1 < ranges.length) {
        slave(ranges[nodeId], ranges[nodeId+1]);
      }
    }
    if (nodeId == 0) {
      long out = 0;
      for (int i = 0; i < activeNodes; i++) {
        out = (out + nodes[i].getlnLong())%PRIME;
      }
      outWriter.println(out);
    }
  }
}
