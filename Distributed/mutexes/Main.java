import java.util.*;

public class Main {
   public static int signum(int i) { return i > 0 ? 1 : i < 0 ? -1 : 0; }
   private static int maxLabel = -1;

   public static void reduceLabels(int[] aOps, int[] bOps) {
      HashSet<Integer> set = new HashSet<Integer>(aOps.length+bOps.length);
      for (int i : aOps) { set.add(i); }
      for (int i : bOps) { set.add(i); }

      HashMap<Integer,Integer> revMap = new HashMap<Integer,Integer>(set.size());
      int ndx = 0;
      for (int i : set) {
         revMap.put(Math.abs(i),ndx++);
      }
      maxLabel = ndx-1;

      for (int i = 0; i < aOps.length; i++) {
         aOps[i] = revMap.get(Math.abs(aOps[i]));
      }
      for (int i = 0; i < bOps.length; i++) {
         bOps[i] = revMap.get(Math.abs(bOps[i]));
      }
   }

   public static void flip(BitSet set, int[] ops, int pos) {
      if (pos >= 0 && pos < ops.length) {
         set.flip(ops[pos]);
      }
   }

   public static void main(String[] args) {
      int numAOps = (int)mutexes.NumberOfOperations(0), numBOps = (int)mutexes.NumberOfOperations(1);
      int[] aOps = new int[numAOps], bOps = new int[numBOps];
      for (int i = 0; i < numAOps; i++) {
         aOps[i] = Math.abs((int)mutexes.GetOperation(0,i));
      }
      for (int i = 0; i < numBOps; i++) {
         bOps[i] = Math.abs((int)mutexes.GetOperation(1,i));
      }
      //reduceLabels(aOps, bOps);

      //System.err.println("aOps:"+Arrays.toString(aOps));
      //System.err.println("bOps:"+Arrays.toString(bOps));
      
      boolean[] fringe = new boolean[Math.max(numAOps,numBOps)];
      BitSet baseSet = new BitSet(maxLabel+1), setA = new BitSet(maxLabel+1), setB = new BitSet(maxLabel+1);
      //for (int dist = 0; dist < numAOps + numBOps; dist++) {
      for (int dist = 0; dist < 7; dist++) {
         flip(baseSet, aOps, dist-1);
         setA.clear();
         setB.clear();
         setA.or(baseSet);
         //System.err.println("base:"+baseSet);

         int runCount = 0;
         //for (int aPos = dist, bPos = 0; aPos > 0; aPos--, bPos++) {
         for (int aPos = dist, bPos = 0; aPos >= 0; aPos--, bPos++) {
            flip(setB, bOps, bPos-1);
            System.err.printf("aPos:%d bPos:%d setA:%s setB:%s\n", aPos, bPos, setA, setB);
            if (setA.intersects(setB)) {
               runCount++;
               //System.err.println("Intersection. ");
               if (runCount == 2) {
                  System.out.println(dist+1);
                  return;
               }
            } else {
               runCount = 0;
            }
            flip(setA, aOps, aPos-1);
         }
      }
      System.out.println("OK");
   }
}
