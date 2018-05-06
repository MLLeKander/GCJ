import java.util.*;
import java.io.*;

interface Messageable {
   public void writeMessage(int to);
}
abstract class MasterArch {
   public abstract Collection<Messageable> jobs();
   public abstract void respondTo(int workerId);
   public abstract void finish();
   public abstract void processWorker();

   public static int[] partitionSizes(int rangeLength, int partitions) {
      int[] out = new int[partitions];
      int midPoint = (rangeLength) % partitions;
      Arrays.fill(out,0,midPoint,(rangeLength)/partitions + 1);
      Arrays.fill(out,midPoint,partitions,(rangeLength)/partitions);
      return out;
   }

   public void assignJobFromQueue(Collection<Messageable> queue, int workerId) {
      if (queue.size() > 0) {
         Messageable job = queue.iterator().next();
         queue.remove(job);

         message.PutChar(workerId, 'c');
         job.writeMessage(workerId);
      } else {
         message.PutChar(workerId, 's');
      }

      message.Send(workerId);
   }

   public void run() {
      if (message.MyNodeId() == 0) {
         runMaster();
      } else {
         runWorker();
      }
   }

   public void runMaster() {
      Collection<Messageable> jobQueue = jobs();
      int jobsToComplete = jobQueue.size();

      for (int node = 1; node < message.NumberOfNodes(); node++) {
         assignJobFromQueue(jobQueue, node);
      }

      for ( ; jobsToComplete > 0; jobsToComplete--) {
         int node = message.Receive(-1);
         respondTo(node);
         assignJobFromQueue(jobQueue, node);
      }

      finish();
   }

   public void runWorker() {
      message.Receive(0);
      while (message.GetChar(0) == 'c') {
         processWorker();
         message.Send(0);
         message.Receive(0);
      }
   }
}

class AlmostSortedMessageable implements Messageable {
   public long startIndex, endIndex;
   public AlmostSortedMessageable(long s, long e) { startIndex=s; endIndex=e; }
   public void writeMessage(int to) { message.PutLL(to, startIndex); message.PutLL(to, endIndex); }
   public String toString() { return "["+startIndex+","+endIndex+"]"; }
}

public class Main extends MasterArch {
   public static void main(String[] args) { new Main().run(); }

   public static final int NUM_PARTS = (int)(message.NumberOfNodes()-1);
   public static final int MOD = 1048576;

   // MASTER NODE LOGIC
   public static int[] parts = new int[NUM_PARTS];
   static {
      int[] partSizes = partitionSizes((int)almost_sorted.NumberOfFiles(), NUM_PARTS);

      int curr = 0;
      for (int i = 0; i < partSizes.length; i++) {
         curr += partSizes[i];
         parts[i] = curr;
      }
   }

   public Collection<Messageable> jobs() {
      HashSet<Messageable> out = new HashSet<Messageable>(parts.length);
      long prev = 0, curr = 0;
      for (int i = 0; i < parts.length; i++) {
         curr = parts[i];
         out.add(new AlmostSortedMessageable(prev,curr));
         prev = curr;
      }
      return out;
   }

   public long hash = 0;
   public void respondTo(int workerId) {
      hash += message.GetLL(workerId);
      hash %= MOD;
   }

   public void finish() {
      System.out.println(hash);
   }


   // WORKER NODE LOGIC
   public void processWorker() {
      long rLow = message.GetLL(0), rHigh = message.GetLL(0);
      long maxD = almost_sorted.MaxDistance();
      long sLow = Math.max(0, rLow-maxD);
      long sHigh = Math.min(rHigh+maxD, almost_sorted.NumberOfFiles());
      long[] ids = new long[(int)(sHigh-sLow)];
      for (long i = sLow; i < sHigh; i++) {
         ids[(int)(i-sLow)] = almost_sorted.Identifier(i);
      }
      Arrays.sort(ids);

      long hash = 0;
      for (int i = (int)(rLow-sLow); i < rHigh-sLow; i++) {
         hash += (ids[i] % MOD) * (i+sLow);
         hash %= MOD;
      }
      message.PutLL(0, hash);
   }
}
