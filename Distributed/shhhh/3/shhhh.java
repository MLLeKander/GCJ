// Sample input 3, in Java.
public class shhhh {
  public shhhh() {
  }

  public static long GetN() {
    return 6L;
  }

  public static long GetLeftNeighbour(long i) {
    switch ((int)i) {
      case 0: return 3L;
      case 3: return 1L;
      case 1: return 4L;
      case 4: return 5L;
      case 5: return 2L;
      case 2: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetRightNeighbour(long i) {
    switch ((int)i) {
      case 0: return 2L;
      case 2: return 5L;
      case 5: return 4L;
      case 4: return 1L;
      case 1: return 3L;
      case 3: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}