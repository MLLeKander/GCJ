public class Main {
  static int MASTER_NODE = 7;
  static int DONE = -1;

  public static void main(String[] args) {
    long N = oops.GetN();
    long nodes = message.NumberOfNodes();
    long my_id = message.MyNodeId();
    long best_so_far = 0L;
    for (long i = 0; i < N; ++i) {
      for (long j = 0; j < N; ++j) {
        if (j % nodes == my_id) {
          long candidate = oops.GetNumber(i) - oops.GetNumber(j);
          if (candidate > best_so_far) {
            best_so_far = candidate;
            message.PutLL(MASTER_NODE, candidate);
            message.Send(MASTER_NODE);
          }
        }
      }
    }
    message.PutLL(MASTER_NODE, DONE);
    message.Send(MASTER_NODE);

    if (my_id == MASTER_NODE) {
      long global_best_so_far = 0;
      for (int node = 0; node < nodes; ++node) {
        long received_candidate = 0;
        while (true) {
          message.Receive(node);
          received_candidate = message.GetLL(node);
          if (received_candidate == DONE) {
            break;
          }
          if (received_candidate > global_best_so_far) {
            global_best_so_far = received_candidate;
          }
        }
      }
      System.out.println(global_best_so_far);
    }
  }
}
