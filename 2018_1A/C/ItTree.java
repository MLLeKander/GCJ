import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class ItTree {
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static final int _BUFFER_SIZE = 1 << 16;
  public static final DataInputStream _din = new DataInputStream(System.in);
  public static final byte[] _buffer = new byte[_BUFFER_SIZE];
  public static int _bufferPointer = 0, _bytesRead = 0;
  public static boolean test(int[][] ranges) {
    BitSet expected = new BitSet();
    for (int[] row : ranges) {
      for (int i = row[0]; i < row[1]+1; i++) {
        expected.set(i);
      }
    }
    Integer[] ndxes = new Integer[ranges.length];
    for (int i = 0; i < ndxes.length; i++) {
      ndxes[i] = i;
    }
    for (int it = 0; it < 100; it++) {
      IntervalTree tree = new IntervalTree();
      for (int ndx : ndxes) {
        int[] row = ranges[ndx];
        tree.add(row[0], row[1]);
      }
      BitSet actual = new BitSet();
      for (IntervalTree.Range r : tree) {
        for (int i = (int)r.lo; i < r.hi+1; i++) {
          actual.set(i);
        }
      }
      if (!actual.equals(expected)) {
        System.err.println("tree:"+(tree));
        return false;
      }
      List<Integer> tmp = Arrays.asList(ndxes);
      Collections.shuffle(tmp, rand);
      tmp.toArray(ndxes);
    }
    return true;
  }
  public static int[][] case1() {
    int[][] out = new int[1000][2];
    for (int i = 0; i < out.length; i++) {
      for (int j = 0; j < out[0].length; j++) {
        out[i][j] = 2*i+j;
      }
    }
    return out;
  }
  public static Random rand = new Random(1);
  public static int[][] case2() {
    int[][] out = new int[1000][ 2];
    for (int i = 0; i < out.length; i++) {
      for (int j = 0; j < out[0].length; j++) {
        out[i][j] = rand.nextInt(100000);
      }
    }
    for (int[] row : out) {
      row[1] += row[0];
    }
    return out;
  }
  public static void main() {
    System.err.println("case1");
    System.err.println("test(case1()):"+(test(case1())));
    System.err.println("case2");
    System.err.println("test(case2()):"+(test(case2())));
  }
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
      this.tailSet(new Range(maxT, maxT), false).clear();
      if (this.size() > 0) {
        Range last = this.last();
        last.hi = Math.min(last.hi,maxT);
      }
    }
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
