// Sample input 3, in Java.
public class oops {
  public oops() {
  }

  public static long GetN() {
    return 15L;
  }

  public static long GetNumber(long i) {
    switch ((int)i) {
      case 0: return 0L;
      case 1: return 1L;
      case 2: return 2L;
      case 3: return 3L;
      case 4: return 4L;
      case 5: return 5L;
      case 6: return 6L;
      case 7: return 7L;
      case 8: return 8L;
      case 9: return 9L;
      case 10: return 10L;
      case 11: return 11L;
      case 12: return 12L;
      case 13: return 13L;
      case 14: return 14L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}