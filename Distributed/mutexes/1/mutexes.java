// Sample input 1, in Java.
public class mutexes {
  public mutexes() {
  }

  public static long NumberOfOperations(long i) {
    switch ((int)i) {
      case 0: return 4;
      case 1: return 4;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetOperation(long i, long index) {
    if (i == 0L && index == 0L) return 1;
    if (i == 0L && index == 1L) return 2;
    if (i == 0L && index == 2L) return -2;
    if (i == 0L && index == 3L) return -1;
    if (i == 1L && index == 0L) return 1;
    if (i == 1L && index == 1L) return 2;
    if (i == 1L && index == 2L) return -1;
    if (i == 1L && index == 3L) return -2;
    throw new IllegalArgumentException("Invalid argument");
  }
}
