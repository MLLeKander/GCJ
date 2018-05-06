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
  public static class Res implements Puttable {
    public long base;
    public boolean carry;
    public long base() { return base; }
    public boolean carry() { return carry; }
    Res(long base_, boolean carry_) { base=base_; carry=carry_; }
    Res(Node n) {
      this(n.getLong(),n.getBool());
    }
    public void putTo(Node n) {
      n.put(base);
      n.put(carry);
    }
    public static Res get(Node n) { return new Res(n); }
    public static Res getln(Node n) { n.receive(); return get(n); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Res o = (Res)oo;
      return base==o.base&&carry==o.carry;
    }
    public int hashCode() { return Objects.hash(base,carry); }
    public String toString() { return ""+base+","+carry; }
  }
  public static long len = number_bases.GetLength();
  public static Res BAD = new Res(0, false);
  public static Res reduce(Res[] noCarries, Res[] carries, boolean carryIn) {
    Res[] path = new Res[carries.length];
    for (int i = 0; i < path.length; i++) { path[i] = (i == 0 ? carryIn : path[i-1].carry) ? carries[i] : noCarries[i]; }
    long base = -2;
    for (Res r : path) {
      long tmpBase = r.base;
      if (tmpBase == 0) {
        return BAD;
      } else if (tmpBase < 0 && base < 0) {
        base = Math.min(base, tmpBase);
      } else if (tmpBase < 0 && base > 0) {
        if (base < -tmpBase) { return BAD; }
      } else if (tmpBase > 0 && base < 0) {
        if (tmpBase < -base) { return BAD; }
        base = tmpBase;
      } else if (tmpBase > 0 && base > 0) {
        if (tmpBase != base) { return BAD; }
      }
    }
    return new Res(base, path[path.length-1].carry);
  }
  public static Res allowedBase(long x, long y, boolean carry, long z) {
    long sum = x + y + (carry ? 1 : 0), base = sum-z;
    if (sum == z) {
      return new Res(-(z+1), false);
    }
    if (base <= x || base <= y || base <= z) {
     return BAD;
    }
    return new Res(base, true);
  }
  public static void slave(long lo, long hi) {
    int range = (int)(hi-lo);
    Res[] carries = new Res[range], noCarries = new Res[range];
    for (int i = 0; i < range; i++) {
      long x = number_bases.GetDigitX(lo+i), y = number_bases.GetDigitY(lo+i), z = number_bases.GetDigitZ(lo+i);
      carries[i] = allowedBase(x, y, true, z);
      noCarries[i] = allowedBase(x, y, false, z);
    }
    masterNode.put(reduce(noCarries,carries,false)).putln(reduce(noCarries,carries,true));
  }
  public static void master() {
    Res[] noCarries = new Res[activeNodes];
    for (int i = 0; i < noCarries.length; i++) { noCarries[i] = Res.getln(nodes[i]); }
    Res[] carries = new Res[activeNodes];
    for (int i = 0; i < carries.length; i++) { carries[i] = Res.get(nodes[i]); }
    Res a = reduce(noCarries, carries, false);
    if (a.base == 0 || a.carry) {
      outWriter.println("IMPOSSIBLE");
    } else if (a.base < 0) {
      outWriter.println("NON-UNIQUE");
    } else if (a.base > 0) {
      outWriter.println(a.base);
    }
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
