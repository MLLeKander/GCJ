// Sample input 3, in Java.
public class query_of_death {
  public query_of_death() {
  }

  static int isthenodebroken = 0;
  //static int testvs[] = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};

  public static long GetLength() {
    return 1000;
  }

  public static long GetValue(long i) {
    if (i < 0L || i >= GetLength())
      throw new IllegalArgumentException("Invalid argument");
    int tmpVal = 1; // (i+1)%2;
    int val = (int)(tmpVal^((int)(Math.random() * 2 + 1) % (isthenodebroken + 1)));
    if (i == 4)
      isthenodebroken = 1;
    return val;
  }
}
