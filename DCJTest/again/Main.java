import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Main {
  public static final PrintStream outWriter = System.out;
  public static class Node {
    int ndx;
    public Node(int ndx_) { ndx=ndx_; }
    public void put(int i) { message.PutInt(ndx, i); } public void putln(int i) { message.PutInt(ndx, i); send(); }
    public void put(char i) { message.PutChar(ndx, i); } public void putln(char i) { message.PutChar(ndx, i); send(); }
    public void put(long i) { message.PutLL(ndx, i); } public void putln(long i) { message.PutLL(ndx, i); send(); }
    public void put(Puttable i) { i.putTo(this); } public void putln(Puttable i) { i.putTo(this); send(); }
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
  public static long LARGE_PRIME = 1000000007L;
  public static long n = again.GetN();
  public static void slave() {
    long sumA = 0, sumB = 0;
    for (long i = nodeId; i < n; i+= nodes.length) {
      sumA = (sumA + again.GetA(i))%LARGE_PRIME;
      sumB = (sumB + again.GetB(i))%LARGE_PRIME;
    }
    masterNode.put(sumA);
    masterNode.putln(sumB);
  }
  public static void master() {
    long[] as = new long[nodes.length];
    for (int i = 0; i < as.length; i++) { as[i] = nodes[i].getlnLong(); }
    long[] bs = new long[nodes.length];
    for (int i = 0; i < bs.length; i++) { bs[i] = nodes[i].getLong(); }
    System.err.println("ats(as):"+(Arrays.toString(as))+"  ats(bs):"+(Arrays.toString(bs)));
    long result = 0;
    for (int i = 0; i < nodes.length; i++) {
      for (int j = 0; j < nodes.length; j++) {
        if ((i+j)%20 != 0) {
          result = (result + as[i]*bs[j])%LARGE_PRIME;
        }
      }
    }
    outWriter.println(result);
  }
  public static void main(String[] args) {
    slave();
    if (nodeId == 0) { master(); }
  }
}
