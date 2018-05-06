// Sample input 3, in Java.
public class kolakoski {
  public kolakoski() {
  }

  public static long GetIndex() {
    return 20L;
  }

  public static long GetMultiplier(long index) {
    switch ((int)index) {
      case 0: return 1L;
      case 1: return 2L;
      case 2: return 2L;
      case 3: return 1L;
      case 4: return 1L;
      case 5: return 2L;
      case 6: return 1L;
      case 7: return 2L;
      case 8: return 2L;
      case 9: return 1L;
      case 10: return 2L;
      case 11: return 2L;
      case 12: return 1L;
      case 13: return 1L;
      case 14: return 2L;
      case 15: return 1L;
      case 16: return 1L;
      case 17: return 2L;
      case 18: return 2L;
      case 19: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}