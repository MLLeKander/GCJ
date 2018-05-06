import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class TestCull {
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static final int _BUFFER_SIZE = 1 << 16;
  public static final DataInputStream _din = new DataInputStream(System.in);
  public static final byte[] _buffer = new byte[_BUFFER_SIZE];
  public static int _bufferPointer = 0, _bytesRead = 0;
  public static class IntervalTree extends TreeSet<IntervalTree.Range> {
  public static class Range implements Comparable<Range> {
    public double lo;
    public double hi;
    public double lo() { return lo; }
    public double hi() { return hi; }
    Range(double lo_, double hi_) { lo=lo_; hi=hi_; }
    Range() {
      this(nextDouble(),nextDouble());
    }
    public static Comparator<Range> cmp = Comparator.comparing(Range::lo).thenComparing(Range::hi);
    public int compareTo(Range o) { return cmp.compare(this, o); }
    public boolean equals(Object oo) {
      @SuppressWarnings("unchecked")
      Range o = (Range)oo;
      return o != null && lo==o.lo&&hi==o.hi;
    }
    public int hashCode() { return Objects.hash(lo,hi); }
    public String toString() { return ""+lo+","+hi; }
  }
    @Override
    public boolean add(Range r) {
      NavigableSet<Range> subset = this.subSet(new Range(r.lo,r.lo), true, new Range(r.hi,r.hi), true);
      Range tmp = new Range(r.lo, r.hi);
      if (subset.size() != 0) {
        tmp.lo = Math.min(tmp.lo,subset.first().lo);
        tmp.hi = Math.max(tmp.hi,subset.last().hi);
      }
      subset.clear();
      Range floor = this.floor(tmp);
      if (floor != null && floor.hi >= tmp.lo) {
        floor.hi = Math.max(floor.hi,tmp.hi);
        return true;
      } else {
        return super.add(tmp);
      }
    }
    public boolean add(double lo, double hi) {
      return add(new Range(lo, hi));
    }
    public IntervalTree expand(double lo, double hi) {
      IntervalTree out = new IntervalTree();
      for (Range r : this) {
        out.add(new Range(lo+r.lo, hi+r.hi));
      }
      return out;
    }
    public void cull(double maxT) {
      this.tailSet(new Range(maxT, Double.MAX_VALUE), false).clear();
      if (this.size() > 0) {
        Range last = this.last();
        last.hi = Math.min(last.hi,maxT);
      }
    }
  }
  public static void main() {
    IntervalTree tree = new IntervalTree();
    for (int i = 0; i < 100; i++) {
      tree.add(2*i, 2*i+1);
    }
    tree.cull(4);
    System.err.println("tree:"+(tree));
  }
  public static void main(String[] args) { main(); outWriter.flush(); }
  public static void _fillBuffer() {
    try {
      _bytesRead = _din.read(_buffer, _bufferPointer = 0, _buffer.length);
      if (_bytesRead == -1)
        _buffer[0] = -1;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
  public static byte _read() {
    if (_bufferPointer == _bytesRead)
      _fillBuffer();
    return _buffer[_bufferPointer++];
  }
  public static String next() {
    byte[] strBuf = new byte[64];
    int cnt = 0, c;
    while ((c = _read()) != -1) {
      if (Character.isWhitespace(c)) {
        if (cnt == 0) {
          continue;
        } else {
          break;
        }
      }
      if (strBuf.length == cnt) {
        strBuf = Arrays.copyOf(strBuf, strBuf.length*2);
      }
      strBuf[cnt++] = (byte) c;
    }
    return new String(strBuf, 0, cnt);
  }
  public static String nextLine() {
    byte[] strBuf = new byte[64];
    int cnt = 0, c;
    while ((c = _read()) != -1) {
      if (c == '\n') {
        if (cnt == 0) {
          continue;
        } else {
          break;
        }
      }
      if (strBuf.length == cnt) {
        strBuf = Arrays.copyOf(strBuf, strBuf.length*2);
      }
      strBuf[cnt++] = (byte) c;
    }
    return new String(strBuf, 0, cnt);
  }
  public static int nextInt() {
    int ret = 0;
    byte c = _read();
    while (c <= ' ')
      c = _read();
    boolean neg = (c == '-');
    if (neg)
      c = _read();
    do {
      ret = ret * 10 + c - '0';
    } while ((c = _read()) >= '0' && c <= '9');
    if (neg)
      return -ret;
    return ret;
  }
  public static long nextLong() {
    long ret = 0;
    byte c = _read();
    while (c <= ' ')
      c = _read();
    boolean neg = (c == '-');
    if (neg)
      c = _read();
    do {
      ret = ret * 10 + c - '0';
    } while ((c = _read()) >= '0' && c <= '9');
    if (neg)
      return -ret;
    return ret;
  }
  public static double nextDouble() {
    double ret = 0, div = 1;
    byte c = _read();
    while (c <= ' ')
      c = _read();
    boolean neg = (c == '-');
    if (neg)
      c = _read();
    do {
      ret = ret * 10 + c - '0';
    }
    while ((c = _read()) >= '0' && c <= '9');
    if (c == '.') {
      while ((c = _read()) >= '0' && c <= '9') {
        ret += (c - '0') / (div *= 10);
      }
    }
    if (neg)
      return -ret;
    return ret;
  }
}
