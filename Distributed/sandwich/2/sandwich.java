// Sample input 2, in Java.
public class sandwich {
  public sandwich() {
  }

  public static long GetN() {
    return 3L;
  }

  public static long GetTaste(long index) {
    switch ((int)index) {
      case 0: return -2L;
      case 1: return 1L;
      case 2: return -2L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}