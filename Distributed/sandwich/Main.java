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

   public static int[] partition(int rangeLength, int partitions) {
      int[] out = new int[partitions];
      int midPoint = (rangeLength) % partitions;
      Arrays.fill(out,0,midPoint,(rangeLength)/partitions + 1);
      Arrays.fill(out,midPoint,partitions,(rangeLength)/partitions);

      for (int i = 1; i < out.length; i++) {
         out[i] += out[i-1];
      }
      return out;
   }

   public int numWorkers() { return (int)message.NumberOfNodes()-1; }

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

class SandwichMessageable implements Messageable {
   public int ndx;
   public long startIndex, endIndex;
   public SandwichMessageable(int n, long s, long e) { ndx=n; startIndex=s; endIndex=e; }
   public void writeMessage(int to) { message.PutInt(to, ndx); message.PutLL(to, startIndex); message.PutLL(to, endIndex); }
   public String toString() { return "["+startIndex+","+endIndex+"]"; }
}

class Portion {
   long sum, left, right, leftR, rightL;
   public Portion(long s, long l, long lr, long r, long rl) { sum=s; left=l; leftR=lr; right=r; rightL=rl; }
}

public class Main extends MasterArch {
   public static void main(String[] args) { new Main().run(); }

   // MASTER NODE LOGIC
   public Collection<Messageable> jobs() {
      int[] parts = partition((int)sandwich.GetN(), numWorkers());

      HashSet<Messageable> out = new HashSet<Messageable>(parts.length);
      long prev = 0, curr = 0;
      for (int i = 0; i < parts.length; i++) {
         curr = parts[i];
         out.add(new SandwichMessageable(i,prev,curr));
         prev = curr;
      }

      return out;
   }

   public Portion[] portions = new Portion[numWorkers()];
   public void respondTo(int workerId) {
      int ndx = message.GetInt(workerId);
      long sum = message.GetLL(workerId);
      long left = message.GetLL(workerId);
      long leftR = message.GetLL(workerId);
      long right = message.GetLL(workerId);
      long rightL = message.GetLL(workerId);
      portions[ndx] = new Portion(sum, left, leftR, right, rightL);
   }

   public long extract(Portion p, int di, boolean eqDest) {
      if (di > 0) {
         if (eqDest) {
            return p.rightL;
         } else {
            return p.left;
         }
      } else {
         if (eqDest) {
            return p.leftR;
         } else {
            return p.right;
         }
      }
   }

   class Tmp {
      int ndx;
      long max;
      public Tmp(long m, int n) { ndx=n; max=m; }
   }

   public void finish() {
      long maxL=0, maxLR=0, maxR=0, maxRL=0;
      int maxLNdx = -1, maxRNdx = -1;
      for (int sum = 0, i = 0; i < portions.length; sum += portions[i].sum, i++) {
         if (sum + portions[i].left > maxL) {
            maxL = sum + portions[i].left;
            maxLNdx = i;
         }
      }

      if (maxLNdx >= 0) {
         int sum = 0;
         for (int i = portions.length-1; i > maxLNdx; sum += portions[i].sum, i--) {
            if (sum + portions[i].right > maxLR) {
               maxLR = sum + portions[i].right;
            }
         }
         maxLR = Math.max(maxLR, sum + portions[maxLNdx].leftR);
      }

      for (int sum = 0, i = portions.length-1; i >= 0; sum += portions[i].sum, i--) {
         if (sum + portions[i].right > maxR) {
            maxR = sum + portions[i].right;
            maxRNdx = i;
         }
      }

      if (maxRNdx >= 0) {
         int sum = 0;
         for (int i = 0; i < maxRNdx; sum += portions[i].sum, i++) {
            if (sum + portions[i].left > maxRL) {
               maxRL = sum + portions[i].left;
            }
         }
         maxRL = Math.max(maxRL, sum + portions[maxRNdx].rightL);
      }

      System.out.println(Math.max(0,Math.max(maxL+maxLR,maxR+maxRL)));
   }


   // WORKER NODE LOGIC
   public Tmp maxWalk(long[] tastes, int start, int di, int dest) {
      long sum = 0, max = 0;
      int maxNdx = -1;

      for (int i = start; start > dest && i > dest || i < dest; i += di) {
         sum += tastes[i];
         if (sum > max) {
            max = sum;
            maxNdx = i;
         }
      }

      return new Tmp(max,maxNdx);
   }
   public void processWorker() {
      int ndx = message.GetInt(0);
      long low = message.GetLL(0), high = message.GetLL(0);
      long[] tastes = new long[(int)(high-low)];
      for (long i = low; i < high; i++) {
         tastes[(int)(i-low)] = sandwich.GetTaste(i);
      }

      long sum = 0;
      for (int i = 0; i < tastes.length; i++) {
         sum += tastes[i];
      }

      Tmp maxL = maxWalk(tastes, 0, 1, tastes.length);
      long maxLR = maxL.ndx >= 0 ? maxWalk(tastes, tastes.length-1, -1, maxL.ndx).max : -1;

      Tmp maxR = maxWalk(tastes, tastes.length-1, -1, -1);
      long maxRL = maxR.ndx >= 0 ? maxWalk(tastes, 0, 1, maxR.ndx).max : -1;

      message.PutInt(0, ndx);
      message.PutLL(0, sum);
      message.PutLL(0, maxL.max);
      message.PutLL(0, maxLR);
      message.PutLL(0, maxR.max);
      message.PutLL(0, maxRL);
   }
}
