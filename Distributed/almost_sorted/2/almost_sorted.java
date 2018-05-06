// Sample input 2, in Java.
public class almost_sorted {
  public almost_sorted() {
  }

  public static long NumberOfFiles() {
    return 4L;
  }

  public static long MaxDistance() {
    return 2L;
  }

  public static long Identifier(long i) {
    switch ((int)i) {
      case 0: return 1000L;
      case 1: return 1500L;
      case 2: return 0L;
      case 3: return 500L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}