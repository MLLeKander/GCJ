// Sample input 2, in Java.
public class flagpoles {
  public flagpoles() {
  }

  public static long GetNumFlagpoles() {
    return 4L;
  }

  public static long GetHeight(long i) {
    switch ((int)i) {
      case 0: return 2L;
      case 1: return 2L;
      case 2: return 2L;
      case 3: return 2L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}