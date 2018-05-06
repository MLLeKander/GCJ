// Sample input 3, in Java.
public class asteroids {
  public asteroids() {
  }

  public static long GetHeight() {
    return 3L;
  }

  public static long GetWidth() {
    return 3L;
  }

  public static char GetPosition(long i, long j) {
    if (i == 2L && j == 0L) return '0';
    if (i == 2L && j == 1L) return '#';
    if (i == 2L && j == 2L) return '#';
    if (i == 1L && j == 0L) return '0';
    if (i == 1L && j == 1L) return '0';
    if (i == 1L && j == 2L) return '0';
    if (i == 0L && j == 0L) return '#';
    if (i == 0L && j == 1L) return '#';
    if (i == 0L && j == 2L) return '0';
    throw new IllegalArgumentException("Invalid argument");
  }
}