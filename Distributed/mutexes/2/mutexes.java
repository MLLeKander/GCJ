// Sample input 2, in Java.
public class mutexes {
  public mutexes() {
  }

  public static long NumberOfOperations(long i) {
    switch ((int)i) {
      case 0: return 5;
      case 1: return 2;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetOperation(long i, long index) {
    if (i == 0L && index == 0L) return 10;
    if (i == 0L && index == 1L) return -10;
    if (i == 0L && index == 2L) return 10;
    if (i == 0L && index == 3L) return 20;
    if (i == 0L && index == 4L) return 30;
    if (i == 1L && index == 0L) return 30;
    if (i == 1L && index == 1L) return 10;
    throw new IllegalArgumentException("Invalid argument");
  }
}
