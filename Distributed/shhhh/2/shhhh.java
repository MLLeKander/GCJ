// Sample input 2, in Java.
public class shhhh {
  public shhhh() {
  }

  public static long GetN() {
    return 5L;
  }

  public static long GetLeftNeighbour(long i) {
    switch ((int)i) {
      case 0: return 4L;
      case 4: return 3L;
      case 3: return 2L;
      case 2: return 1L;
      case 1: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetRightNeighbour(long i) {
    switch ((int)i) {
      case 0: return 1L;
      case 1: return 2L;
      case 2: return 3L;
      case 3: return 4L;
      case 4: return 0L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}