// Sample input 2, in Java.
public class broken_memory {
  public broken_memory() {
  }

  public static long GetLength() {
    return 30L;
  }

  public static long GetValue(long i) {
    if (i < 0L || i >= GetLength())
      throw new IllegalArgumentException("Invalid argument");
    if ((29L - message.MyNodeId()) == i)
      return (((i % 9L) + 1L) * ((i % 7L) + 1L) ^ (i + message.MyNodeId() + 1)) + 1L;
    return (((i % 9L) + 1L) * ((i % 7L) + 1L)) + 1L;
  }
}
