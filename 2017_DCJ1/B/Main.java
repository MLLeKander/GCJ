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
  public static long len = weird_editor.GetNumberLength();
  public static long MOD = 1000000007L;
  public static BigInteger TEN = new BigInteger("10");
  public static BigInteger MOD_BIGINT = new BigInteger(MOD+"");
  public static long tenModPow(long p) {
    return TEN.modPow(new BigInteger(p+""),MOD_BIGINT).longValue();
  }
  public static HashMap<Long,Long> memo = new HashMap<Long,Long>();
  public static long onesMod(long n) {
    if (n == 0) { return 0; }
    if (n == 1) { return 1; }
    if (memo.containsKey(n)) { return memo.get(n); }
    long tmp = onesMod(n/2), tmp2 = onesMod((n+1)/2);
    long out = (tmp + (tmp2*tenModPow(n/2))%MOD)%MOD;
    memo.put(n, out);
    return out;
  }
  public static void clearBelow(long[] arr, int ndx) {
    for (int i = 0; i < ndx; i++) {
      arr[i] = 0;
    }
  }
  public static void slave(long lo, long hi) {
    int range = (int)(hi-lo);
    long[] counts = new long[10];
    for (long i = lo; i < hi; i++) {
      int digit = (int)weird_editor.GetDigit(i);
      counts[digit]++;
      clearBelow(counts, digit);
    }
    masterNode.putln(counts);
  }
  public static void master() {
    long[] counts = nodes[0].getlnLongA();
    for (int i = 1; i < activeNodes; i++) {
      long[] tmp = nodes[i].getlnLongA();
      for (int j = 9; j >= 0; j--) {
        if (tmp[j] > 0) {
          counts[j] += tmp[j];
          clearBelow(counts, j);
        }
      }
    }
    long sum = 0, out = 0;
    for (int i = 0; i < 10; i++) { sum += counts[i]; }
    counts[0] += len-sum;
    for (int i = 9; i >= 0; i--) {
      out = ((tenModPow(counts[i])*out)%MOD + onesMod(counts[i])*i)%MOD;
    }
    outWriter.println(out);
  }
  public static void main(String[] args) {
    {
      long[] ranges = linspace(len, nodes.length);
      activeNodes = ranges.length-1;
      if (nodeId+1 < ranges.length) {
        long lo = ranges[nodeId], hi = ranges[nodeId+1];
        slave(lo, hi);
      }
    }
    if (nodeId == 0) { master(); }
  }
}
