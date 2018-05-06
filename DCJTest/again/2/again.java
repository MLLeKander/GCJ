// Sample input 2, in Java.
public class again {
  public again() {
  }

  public static long GetN() {
    return 12L;
  }

  public static long GetA(long i) {
    switch ((int)i) {
      case 0: return 10L;
      case 1: return 10L;
      case 2: return 10L;
      case 3: return 10L;
      case 4: return 10L;
      case 5: return 10L;
      case 6: return 10L;
      case 7: return 10L;
      case 8: return 10L;
      case 9: return 10L;
      case 10: return 10L;
      case 11: return 10L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }

  public static long GetB(long i) {
    switch ((int)i) {
      case 0: return 10L;
      case 1: return 1L;
      case 2: return 1L;
      case 3: return 1L;
      case 4: return 1L;
      case 5: return 1L;
      case 6: return 1L;
      case 7: return 1L;
      case 8: return 1L;
      case 9: return 1L;
      case 10: return 1L;
      case 11: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}