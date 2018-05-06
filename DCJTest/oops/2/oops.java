// Sample input 2, in Java.
public class oops {
  public oops() {
  }

  public static long GetN() {
    return 1L;
  }

  public static long GetNumber(long i) {
    switch ((int)i) {
      case 0: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}