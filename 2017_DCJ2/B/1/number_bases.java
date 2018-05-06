// Sample input 1, in Java.
public class number_bases {
  public number_bases() {
  }

  public static long GetLength() {
    return 3L;
  }

  public static long GetDigitX(long i) {
    switch ((int)i) {
      case 0: return 3L;
      case 1: return 2L;
      case 2: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitY(long i) {
    switch ((int)i) {
      case 0: return 6L;
      case 1: return 5L;
      case 2: return 4L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitZ(long i) {
    switch ((int)i) {
      case 0: return 0L;
      case 1: return 8L;
      case 2: return 5L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}