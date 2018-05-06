// Sample input 2, in Java.
public class number_bases {
  public number_bases() {
  }

  public static long GetLength() {
    return 3L;
  }

  public static long GetDigitX(long i) {
    switch ((int)i) {
      case 0: return 1000L;
      case 1: return 20000L;
      case 2: return 300000L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitY(long i) {
    switch ((int)i) {
      case 0: return 40000L;
      case 1: return 500000L;
      case 2: return 6L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetDigitZ(long i) {
    switch ((int)i) {
      case 0: return 41000L;
      case 1: return 520000L;
      case 2: return 300006L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}