// Sample input 2, in Java.
public class rps {
  public rps() {
  }

  public static long GetN() {
    return 2L;
  }

  public static char GetFavoriteMove(long id) {
    switch ((int)id) {
      case 0: return 'R';
      case 1: return 'R';
      case 2: return 'R';
      case 3: return 'R';
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}