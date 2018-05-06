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
  public static class Res implements Puttable {
    public int range;
    public long lHeight;
    public long lSlope;
    public long lLen;
    public long rHeight;
    public long rSlope;
    public long rLen;
    public long maxLen;
    public int range() { return range; }
    public long lHeight() { return lHeight; }
    public long lSlope() { return lSlope; }
    public long lLen() { return lLen; }
    public long rHeight() { return rHeight; }
    public long rSlope() { return rSlope; }
    public long rLen() { return rLen; }
    public long maxLen() { return maxLen; }
    Res(int range_, long lHeight_, long lSlope_, long lLen_, long rHeight_, long rSlope_, long rLen_, long maxLen_) { range=range_; lHeight=lHeight_; lSlope=lSlope_; lLen=lLen_; rHeight=rHeight_; rSlope=rSlope_; rLen=rLen_; maxLen=maxLen_; }
    Res(Node n) {
      this(n.getInt(),n.getLong(),n.getLong(),n.getLong(),n.getLong(),n.getLong(),n.getLong(),n.getLong());
    }
    public void putTo(Node n) {
      n.put(range);
      n.put(lHeight);
      n.put(lSlope);
      n.put(lLen);
      n.put(rHeight);
      n.put(rSlope);
      n.put(rLen);
      n.put(maxLen);
    }
    public static Res get(Node n) { return new Res(n); }
    public static Res getln(Node n) { n.receive(); return get(n); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Res o = (Res)oo;
      return range==o.range&&lHeight==o.lHeight&&lSlope==o.lSlope&&lLen==o.lLen&&rHeight==o.rHeight&&rSlope==o.rSlope&&rLen==o.rLen&&maxLen==o.maxLen;
    }
    public int hashCode() { return Objects.hash(range,lHeight,lSlope,lLen,rHeight,rSlope,rLen,maxLen); }
    public String toString() { return ""+range+","+lHeight+","+lSlope+","+lLen+","+rHeight+","+rSlope+","+rLen+","+maxLen; }
  }
  public static long poles = flagpoles.GetNumFlagpoles();
  public static void slave(long lo, long hi) {
    int range = (int)(hi-lo);
    long[] data = new long[range];
    for (int i = 0; i < data.length; i++) { data[i] = flagpoles.GetHeight(lo+i); }
    if (range == 1) {
      throw new RuntimeException();
    }
    long lHeight = data[0], lSlope = data[1] - data[0], lLen = 2;
    for (int i = 2; i < range; i++) {
      if (data[i] == data[i-1] + lSlope) {
        lLen++;
      } else {
        break;
      }
    }
    long rHeight = data[range-1], rSlope = data[range-1] - data[range-2], rLen = 2;
    for (int i = range-2; i > 0; i--) {
      if (data[i] == data[i-1] + rSlope) {
        rLen++;
      } else {
        break;
      }
    }
    long height = data[0], slope = data[1]-data[0], len = 1, maxLen = -1;
    for (int i = 1; i < range; i++) {
      if (data[i] == data[i-1]+slope) {
        len++;
      } else {
        maxLen = Math.max(maxLen, len);
        height = data[i];
        slope = data[i]-data[i-1];
        len = 2;
      }
    }
    maxLen = Math.max(maxLen, len);
    masterNode.putln(new Res(range, lHeight, lSlope, lLen, rHeight, rSlope, rLen, maxLen));
  }
  public static void master() {
    Res[] ress = new Res[activeNodes];
    for (int i = 0; i < ress.length; i++) { ress[i] = Res.getln(nodes[i]); }
    long maxLen = -1;
    for (Res r : ress) {
      maxLen = Math.max(maxLen,r.maxLen);
    }
    long rollLen = ress[0].rLen;
    for (int i = 1; i < activeNodes; i++) {
      Res res = ress[i], prevRes = ress[i-1];
      long lLen = res.lLen, rLen = res.rLen, range = res.range;
      long height = res.lHeight, slope = res.lSlope;
      long prevHeight = prevRes.rHeight, prevSlope = prevRes.rSlope;
      if (prevHeight + prevSlope == height && prevSlope == slope) {
        rollLen += lLen;
        maxLen = Math.max(maxLen,rollLen);
        if (range != lLen) {
          rollLen = rLen;
        }
      } else {
        if (prevHeight + prevSlope == height) {
          rollLen++;
        }
        maxLen = Math.max(maxLen,rollLen);
        rollLen = rLen;
      }
    }
    maxLen = Math.max(maxLen,rollLen);
    outWriter.println(maxLen);
  }
  public static void main(String[] args) {
    if (poles == 1) {
      if (nodeId == 0) {
        outWriter.println(1);
      }
    } else if (nodes.length*2 >= poles) {
      if (nodeId == 0) {
        slave(0, poles);
        Res tmp = Res.getln(nodes[0]);
        outWriter.println(tmp.maxLen);
      }
    } else {
      {
        long[] ranges = linspace(poles, nodes.length);
        activeNodes = ranges.length-1;
        if (nodeId+1 < ranges.length) {
          long lo = ranges[nodeId], hi = ranges[nodeId+1];
          slave(lo, hi);
        }
      }
      if (nodeId == 0) { master(); }
    }
  }
}
