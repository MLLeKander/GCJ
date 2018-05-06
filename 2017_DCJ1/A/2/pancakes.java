// Sample input 2, in Java.
public class pancakes {
  public pancakes() {
  }

  public static long GetStackSize() {
    return 6L;
  }

  public static long GetNumDiners() {
    return 4L;
  }

  public static long GetStackItem(long i) {
    switch ((int)i) {
      case 0: return 0L;
      case 1: return 0L;
      case 2: return 0L;
      case 3: return 2L;
      case 4: return 2L;
      case 5: return 3L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}