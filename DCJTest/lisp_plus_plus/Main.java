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
  public static long[] linspace(long hi, int n) {
    long[] out = new long[n];
    for (int lo = 0, i = 0; i < out.length; i++) {
      lo += (hi-lo)/n--;
      out[i] = lo;
    }
    return out;
  }
  public static long N = lisp_plus_plus.GetLength();
  public static char[] cs = new char[0];
  public static void slave(long lo, long hi) {
    cs = new char[(int)(hi-lo)];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = lisp_plus_plus.GetCharacter(i+lo);
    }
    String s = new String(cs);
    long openCount = 0, closeCount = 0;
    for (char c : cs) {
      if (c == '(') {
        openCount++;
      } else {
        if (openCount == 0) {
          closeCount++;
        } else {
          openCount--;
        }
      }
    }
    masterNode.put(closeCount).putln(openCount);
  }
  public static void master() {
    long[] ranges = linspace(N, nodes.length);
    long[] closes = new long[activeNodes];
    for (int i = 0; i < closes.length; i++) { closes[i] = nodes[i].getlnLong(); }
    long[] opens = new long[activeNodes];
    for (int i = 0; i < opens.length; i++) { opens[i] = nodes[i].getLong(); }
    long openCount = 0;
    int mark = -1;
    for (int i = 0; i < activeNodes; i++) {
      openCount -= closes[i];
      if (openCount < 0) {
        mark = i;
        openCount += closes[i];
        break;
      }
      openCount += opens[i];
    }
    if (mark == -1) {
      outWriter.println(openCount == 0 ? -1L : N);
    }
    for (int i = 0; i < nodes.length; i++) {
      if (i == mark) {
        nodes[i].put(openCount).putln(i == 0 ? 0L : ranges[i-1]);
      } else {
        nodes[i].putln(-1L);
      }
    }
  }
  public static void slave2() {
    long open = masterNode.getlnLong();
    if (open == -1) {
      return;
    }
    long prefix = masterNode.getLong();
    for (int i = 0; i < cs.length; i++) {
      if (cs[i] == '(') {
        open++;
      } else {
        if (open > 0) {
          open--;
        } else {
          outWriter.println(prefix+i);
          return;
        }
      }
    }
  }
  public static void main(String[] args) {
    {
      long[] ranges = linspace(N, nodes.length);
      activeNodes = ranges.length;
      if (nodeId < ranges.length) {
        long lo = nodeId == 0 ? 0 : ranges[nodeId-1], hi = ranges[nodeId];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
    slave2();
  }
}
