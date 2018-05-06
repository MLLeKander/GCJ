// Sample input 2, in Java.
public class query_of_death {
  public query_of_death() {
  }

  static int isthenodebroken = 0;
  static int testvs[] = {1, 1, 1};

  public static long GetLength() {
    return 3L;
  }

  public static long GetValue(long i) {
    if (i < 0L || i >= GetLength())
      throw new IllegalArgumentException("Invalid argument");
    int val = (int)(testvs[(int)i]^((int)(Math.random() * 2 + 1) % (isthenodebroken + 1)));
    if (i == 0)
      isthenodebroken = 1;
    return val;
  }
}
