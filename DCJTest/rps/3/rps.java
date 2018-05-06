// Sample input 3, in Java.
public class rps {
  public rps() {
  }

  public static long GetN() {
    return 2L;
  }

  public static char GetFavoriteMove(long id) {
    switch ((int)id) {
      case 0: return 'S';
      case 1: return 'R';
      case 2: return 'P';
      case 3: return 'P';
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}