import java.util.*;

public class Main {
   public static long solve() {
      BitSet bits = new BitSet();
      bits.set(1);
      bits.set(2);
      boolean prev = true;
      int head = 3, tail = 2;
      long sum = 5;
      long max = kolakoski.GetIndex();
      if (max == 1) return 1;
      if (max < 4) return 2*max - 1;
      for ( ; head < max; tail++, head++) {
         prev = !prev;
         bits.set(head, prev);
         sum += prev ? 2 : 1;
         if (bits.get(tail)) {
            bits.set(++head, prev);
            sum += prev ? 2 : 1;
         }
      }
      return sum;
   }
   public static void main(String[] args) {
      if (message.MyNodeId() != 0) { return; }
      System.out.println(solve());
   }
}
