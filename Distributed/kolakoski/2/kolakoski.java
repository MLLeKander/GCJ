// Sample input 2, in Java.
public class kolakoski {
  public kolakoski() {
  }

  public static long GetIndex() {
    return 10L;
  }

  public static long GetMultiplier(long index) {
    switch ((int)index) {
      case 0: return 1L;
      case 1: return 1L;
      case 2: return 1L;
      case 3: return 1L;
      case 4: return 1L;
      case 5: return 1L;
      case 6: return 1L;
      case 7: return 1L;
      case 8: return 1L;
      case 9: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}