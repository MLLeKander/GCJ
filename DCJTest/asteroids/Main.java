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
  public static int width = (int)asteroids.GetWidth();
  public static int height = (int)asteroids.GetHeight();
  public static void master() {
    long[] score = new long[width], scoreNew = new long[width];
    for (int r = 0; r < height; r++) {
      char[] chars = new char[width];
      for (int i = 0; i < chars.length; i++) { chars[i] = asteroids.GetPosition(r,i); }
      Arrays.fill(scoreNew, -1);
      for (int i = 0; i < width; i++) {
        if (chars[i] == '#' || score[i] == -1) { continue; }
        scoreNew[i] = Math.max(scoreNew[i],score[i]+(chars[i]-'0'));
      }
      for (int i = 0; i < width-1; i++) {
        if (chars[i] == '#' || chars[i+1] == '#' || score[i] == -1) { continue; }
        scoreNew[i+1] = Math.max(scoreNew[i+1],score[i]+(chars[i]-'0')+(chars[i+1]-'0'));
      }
      for (int i = 1; i < width; i++) {
        if (chars[i] == '#' || chars[i-1] == '#' || score[i] == -1) { continue; }
        scoreNew[i-1] = Math.max(scoreNew[i-1],score[i]+(chars[i]-'0')+(chars[i-1]-'0'));
      }
      long[] tmp = score;
      score = scoreNew;
      scoreNew = tmp;
    }
    long max = score[0];
    int maxNdx = 0;
    for (int i = 0; i < score.length; i++) {
      if (max < score[i]) {
        max = score[i];
        maxNdx = i;
      }
    }
    outWriter.println(max);
  }
  public static void main(String[] args) {
    if (nodeId == 0) { master(); }
  }
}
