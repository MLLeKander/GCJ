// Sample input 1, in Java.
public class todd_and_steven {
  public todd_and_steven() {
  }

  public static long GetToddLength() {
    return 1L;
  }

  public static long GetStevenLength() {
    return 2L;
  }

  public static long GetToddValue(long i) {
    switch ((int)i) {
      case 0: return 3L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetStevenValue(long i) {
    switch ((int)i) {
      case 0: return 2L;
      case 1: return 6L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}