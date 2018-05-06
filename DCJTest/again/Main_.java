public class Main {
  static int MASTER_NODE = 0;
  static long SENDING_DONE = -1;
  static long LARGE_PRIME = 1000000007;

  public static void master() {
    long result = 0;
    for (int node = 1; node < message.NumberOfNodes(); ++node) {
      while (true) {
        message.Receive(node);
        long value = message.GetLL(node);
        if (value == SENDING_DONE) {
          break;
        } else {
          result = (result + value) % LARGE_PRIME;
        }
      }
    }
    System.out.println(result);
  }

  public static void slave() {
    for (long i = 0; i < again.GetN(); ++i) {
      for (long j = 0; j < again.GetN(); ++j) {
        long value = again.GetA(i) * again.GetB(j);
        if ((i + j) % message.NumberOfNodes() == message.MyNodeId()) {
          message.PutLL(MASTER_NODE, value);
          message.Send(MASTER_NODE);
        }
      }
    }
    message.PutLL(MASTER_NODE, SENDING_DONE);
    message.Send(MASTER_NODE);
  }

  public static void main(String[] args) {
    if (message.MyNodeId() == MASTER_NODE) {
      master();
    } else {
      slave();
    }
  }
}
