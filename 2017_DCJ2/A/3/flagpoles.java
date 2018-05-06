// Sample input 3, in Java.
public class flagpoles {
  public flagpoles() {
  }

  public static long GetNumFlagpoles() {
    return 5L;
  }

  public static long GetHeight(long i) {
    switch ((int)i) {
      case 0: return 1L;
      case 1: return 3L;
      case 2: return 2L;
      case 3: return 4L;
      case 4: return 3L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}
