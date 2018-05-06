// Sample input 1, in Java.
public class shhhh {
  public shhhh() {
  }

  public static long GetN() {
    return 2L;
  }

  public static long GetLeftNeighbour(long i) {
    switch ((int)i) {
      case 0: return 1L;
      case 1: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetRightNeighbour(long i) {
    switch ((int)i) {
      case 0: return 1L;
      case 1: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}