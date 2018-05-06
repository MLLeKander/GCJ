// Sample input 3, in Java.
public class lisp_plus_plus {
  public lisp_plus_plus() {
  }

  public static long GetLength() {
    return 1L;
  }

  public static char GetCharacter(long index) {
    switch ((int)index) {
      case 0: return ')';
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}