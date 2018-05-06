// Sample input 1, in Java.
public class number_bases {
  public number_bases() {
  }

  public static long GetLength() {
    return 1L;
  }

  public static long GetDigitX(long i) {
    switch ((int)i) {
      case 0: return 10L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitY(long i) {
    switch ((int)i) {
      case 0: return 20L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitZ(long i) {
    switch ((int)i) {
      case 0: return 30L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}
