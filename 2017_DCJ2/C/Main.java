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
    public Node put(boolean i) { message.PutChar(ndx, i ? 'T' : 'F'); return this; } public void putln(boolean i) { message.PutChar(ndx, i ? 'T' : 'F'); send(); } public void put(boolean[] arr) { put(arr.length); for (boolean i : arr) { message.PutChar(ndx, i ? 'T' : 'F'); } } public void putln(boolean[] arr) { put(arr); send(); }
    public Node put(Puttable i) { i.putTo(this); return this; } public void putln(Puttable i) { i.putTo(this); send(); } public void put(Puttable[] arr) { put(arr.length); for (Puttable i : arr) { i.putTo(this); } } public void putln(Puttable[] arr) { put(arr); send(); }
    public int getInt() { return message.GetInt(ndx); } public int getlnInt() { receive(); return message.GetInt(ndx); } public int[] getIntA() { int[] out = new int[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getInt(); } return out; } public int[] getlnIntA() { receive(); return getIntA(); }
    public long getLong() { return message.GetLL(ndx); } public long getlnLong() { receive(); return message.GetLL(ndx); } public long[] getLongA() { long[] out = new long[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getLong(); } return out; } public long[] getlnLongA() { receive(); return getLongA(); }
    public char getChar() { return message.GetChar(ndx); } public char getlnChar() { receive(); return message.GetChar(ndx); } public char[] getCharA() { char[] out = new char[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getChar(); } return out; } public char[] getlnCharA() { receive(); return getCharA(); }
    public boolean getBool() { return message.GetChar(ndx) == 'T'; } public boolean getlnBool() { receive(); return message.GetChar(ndx) == 'T'; } public boolean[] getBoolA() { boolean[] out = new boolean[getInt()]; for (int i = 0; i < out.length; i++) { out[i] = getBool(); } return out; } public boolean[] getlnBoolA() { receive(); return getBoolA(); }
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
  public static long len = broken_memory.GetLength();
  public static int M(int n) {
    return (n+nodes.length)%nodes.length;
  }
  public static void slave() {
    long[] vals = new long[(int)len];
    for (int i = 0; i < vals.length; i++) { vals[i] = broken_memory.GetValue(i); }
    int to1 = M(nodeId+1), to2 = M(nodeId+2);
    int from1 = M(nodeId-1), from2 = M(nodeId-2);
    int partner = nodeId%2 == 0 ? nodeId+1 : nodeId-1;
    nodes[to1].putln(vals);
    nodes[to2].putln(vals);
    long[] c1 = nodes[from1].getlnLongA();
    long[] c2 = nodes[from2].getlnLongA();
    int ndx = -1;
    for (int i = 0; i < len; i++) {
      if (vals[i] != c1[i] && vals[i] != c2[i]) {
        if (ndx != -1) { throw new RuntimeException(); }
        ndx = i;
      }
    }
    masterNode.putln(ndx);
  }
  public static void master() {
    for (int i = 0; i < nodes.length; i++) {
      outWriter.print((i==0?"":" ")+nodes[i].getlnInt());
    }
    outWriter.println();
  }
  public static void main(String[] args) {
    slave();
    if (nodeId == 0) { master(); }
  }
}
