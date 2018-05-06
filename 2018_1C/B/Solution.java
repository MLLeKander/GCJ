import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static final int _BUFFER_SIZE = 1 << 16;
  public static final DataInputStream _din = new DataInputStream(System.in);
  public static final byte[] _buffer = new byte[_BUFFER_SIZE];
  public static int _bufferPointer = 0, _bytesRead = 0;
  public static void main() {
    int t = nextInt();
    while (t-- > 0) {
      solve();
    }
  }
  public static void solve() {
    int n = nextInt();
    int[] counts = new int[n];
    for (int _i = 0; _i < n; _i++) {
      int d = nextInt();
      int[] prefs = new int[d];
      for (int i = 0; i < prefs.length; i++) {
        prefs[i] = nextInt();
      }
      int out = -1, min = Integer.MAX_VALUE;
      for (int p : prefs) {
        if (counts[p] < min) {
          out = p;
          min = counts[p];
        }
        if (counts[p] != Integer.MAX_VALUE) {
          counts[p]++;
        }
      }
      if (out >= 0) {
        counts[out] = Integer.MAX_VALUE;
      }
      outWriter.println(out);
      outWriter.flush();
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
