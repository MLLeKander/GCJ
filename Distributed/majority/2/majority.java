// Sample input 2, in Java.
public class majority {
  public majority() {
  }

  public static long GetN() {
    return 2L;
  }

  public static long GetVote(long i) {
    switch ((int)i) {
      case 0: return 10L;
      case 1: return 20L;
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}