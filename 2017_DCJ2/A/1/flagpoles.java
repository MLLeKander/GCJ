// Sample input 1, in Java.
public class flagpoles {
  public flagpoles() {
  }

  public static long GetNumFlagpoles() {
    return 7L;
  }

  public static long GetHeight(long i) {
    switch ((int)i) {
      case 0: return 5L;
      case 1: return 7L;
      case 2: return 5L;
      case 3: return 3L;
      case 4: return 1L;
      case 5: return 2L;
      case 6: return 3L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}