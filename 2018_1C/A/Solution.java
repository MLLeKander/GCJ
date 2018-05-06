import java.util.*;
import java.util.function.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static String solve(HashSet<String> ss, ArrayList<HashSet<Character>> sets, char[] arr, int depth) {
    if (depth == sets.size()) {
      String out = new String(arr);
      return ss.contains(out) ? null : out;
    }
    for (char c : sets.get(depth)) {
      arr[depth] = c;
      String tmp = solve(ss, sets, arr, depth+1);
      if (tmp != null) { return tmp; }
    }
    return null;
  }
  public static String solve(HashSet<String> ss, ArrayList<HashSet<Character>> sets) {
    return solve(ss, sets, new char[sets.size()], 0);
  }
  public static Object solve() {
    int n = nextInt(), l = nextInt();
    String[] ss = new String[n];
    for (int i = 0; i < ss.length; i++) {
      ss[i] = next();
    }
    ArrayList<HashSet<Character>> sets = new ArrayList<HashSet<Character>>();
    for (int i = 0; i < l; i++) {
      sets.add(new HashSet<Character>());
    }
    for (String s : ss) {
      for (int i = 0; i < l; i++) {
        sets.get(i).add(s.charAt(i));
      }
    }
    String out = solve(new HashSet<String>(Arrays.asList(ss)), sets);
    return out == null ? '-' : out;
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
