// Sample input 1, in Java.
public class kolakoski {
  public kolakoski() {
  }

  public static long GetIndex() {
    return 1L;
  }

  public static long GetMultiplier(long index) {
    switch ((int)index) {
      case 0: return 1L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}