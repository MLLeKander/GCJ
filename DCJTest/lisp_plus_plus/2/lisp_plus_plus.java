// Sample input 2, in Java.
public class lisp_plus_plus {
  public lisp_plus_plus() {
  }

  public static long GetLength() {
    return 5L;
  }

  public static char GetCharacter(long index) {
    switch ((int)index) {
      case 0: return '(';
      case 1: return '(';
      case 2: return ')';
      case 3: return ')';
      case 4: return ')';
      default: throw new IllegalArgumentException("Invalid argument");
    }
  }
}