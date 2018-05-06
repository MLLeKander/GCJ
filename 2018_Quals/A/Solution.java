import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static Object solve() {
    int d = nextInt();
    PriorityQueue<Integer> shots = new PriorityQueue<Integer>(Collections.reverseOrder());
    int power = 1, sum = 0;
    for (char c : next().toCharArray()) {
      if (c == 'C') {
        power <<= 1;
      } else if (c == 'S') {
        sum += power;
        shots.add(power);
      } else {
        throw new RuntimeException();
      }
    }
    if (shots.size() == 0) {
      return 0;
    }
    int count = 0;
    while (shots.peek() > 1 && sum > d) {
      int tmp = shots.poll()/2;
      sum -= tmp;
      shots.add(tmp);
      count++;
    }
    return sum <= d ? count : "IMPOSSIBLE";
  }
  public static void main(String[] args) {
    int T = nextInt();
    for (int i = 0; i < T; i++) {
      outWriter.print("Case #"+(i+1)+": ");
      Object tmp = solve();
      if (tmp != null) { outWriter.println(tmp); }
    }
    outWriter.flush();
  }
  public static StringTokenizer tokenizer = null;
  public static String nextLine() {
    try { return buffer.readLine(); } catch (IOException e) { throw new UncheckedIOException(e); }
  }
  public static String next() {
    while (tokenizer == null || !tokenizer.hasMoreElements()) { tokenizer = new StringTokenizer(nextLine()); }
    return tokenizer.nextToken();
  }
  public static int nextInt() { return Integer.parseInt(next()); }
  public static long nextLong() { return Long.parseLong(next()); }
  public static double nextDouble() { return Double.parseDouble(next()); }
}
