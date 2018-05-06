// Sample input 1, in Java.
public class broken_memory {
  public broken_memory() {
  }

  public static long GetLength() {
    return 10L;
  }

  public static long GetValue(long i) {
    if (i < 0L || i >= GetLength())
      throw new IllegalArgumentException("Invalid argument");
    if ((message.MyNodeId()) == i)
      return (i ^ (i + message.MyNodeId() + 1)) + 1L;
    return (i) + 1L;
  }
}
