// Sample input 3, in Java.
public class todd_and_steven {
  public todd_and_steven() {
  }

  public static long GetToddLength() {
    return 2L;
  }

  public static long GetStevenLength() {
    return 4L;
  }

  public static long GetToddValue(long i) {
    switch ((int)i) {
      case 0: return 15L;
      case 1: return 23L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetStevenValue(long i) {
    switch ((int)i) {
      case 0: return 4L;
      case 1: return 8L;
      case 2: return 16L;
      case 3: return 42L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}