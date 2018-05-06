import java.util.*;
import java.io.*;

interface Messageable {
   public void writeMessage(int to);
}
abstract class MasterNode {
   public abstract Collection<Messageable> jobs();
   public abstract Messageable terminalJob();
   public abstract void respondTo(int workerId);
   public abstract void finish();

   public static int[] partitionSizes(int rangeLength, int partitions) {
      int[] out = new int[partitions];
      int midPoint = (rangeLength) % partitions;
      Arrays.fill(out,0,midPoint,(rangeLength)/partitions + 1);
      Arrays.fill(out,midPoint,partitions,(rangeLength)/partitions);
      return out;
   }

   public void assignJobFromQueue(Collection<Messageable> queue, int workerId) {
      Messageable job = null;
      if (queue.size() > 0) {
         job = queue.iterator().next();
         queue.remove(job);
      } else {
         job = terminalJob();
      }

      job.writeMessage(workerId);
      message.Send(workerId);
   }

   public void run() {
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
}

class ShhhhMessageable implements Messageable {
   public long index;
   public ShhhhMessageable(long i) { index=i; }
   public void writeMessage(int to) { message.PutLL(to, index); }
   public String toString() { return ""+index; }
}

class Path {
   long source, dest, dist;
   public Path(long s, long de, long di) { source=s; dest=de; dist=di; }
   public String toString() { return "("+source+"-"+dist+"-"+dest+")"; }
}

class ShhhhMasterNode extends MasterNode {
   public static final int NUM_PARTS = (int)(message.NumberOfNodes()-1)*2;
   public static long[] parts = new long[NUM_PARTS+1];
   static {
      int[] partSizes = partitionSizes((int)shhhh.GetN(), NUM_PARTS);

      parts[0] = 0;
      parts[1] = 1;

      long curr = partSizes[0];
      for (int i = 1; i < partSizes.length; i++) {
         parts[i+1] = curr;
         curr += partSizes[i];
      }
   }

   public Collection<Messageable> jobs() {
      HashSet<Messageable> out = new HashSet<Messageable>(parts.length);
      out.add(new ShhhhMessageable(1));
      for (long i : parts) {
         if (i < shhhh.GetN()) {
            out.add(new ShhhhMessageable(i));
         }
      }

      return out;
   }

   public Messageable terminalJob() { return new ShhhhMessageable(-1); }

   public HashMap<Long,Path> paths = new HashMap<Long,Path>(jobs().size());
   public void respondTo(int workerId) {
      long source = message.GetLL(workerId);
      long dest = message.GetLL(workerId);
      long dist = message.GetLL(workerId);
      paths.put(source,new Path(source, dest, dist));
   }

   public void finish() {
      long distL = 0, distR = 0;
      for (long curr = 0; curr != 1; ) {
         Path p = paths.get(curr);
         distL += p.dist;
         curr = p.dest;
      }
      for (long curr = 1; curr != 0; ) {
         Path p = paths.get(curr);
         distR += p.dist;
         curr = p.dest;
      }

      String text = distL == distR ? "WHATEVER" : distL > distR ? "RIGHT" : "LEFT";
      System.out.println(text+" "+Math.min(distL,distR));
   }
}

public class Main {
   public static void main(String[] args) {
      if (message.MyNodeId() == 0) {
         new ShhhhMasterNode().run();
      } else {
         message.Receive(0);
         long source = message.GetLL(0);
         while (source >= 0) {
            long curr = source;
            long dist = 0;
            do {
               curr = shhhh.GetLeftNeighbour(curr);
               dist++;
            } while (Arrays.binarySearch(ShhhhMasterNode.parts, curr) < 0);

            message.PutLL(0, source);
            message.PutLL(0, curr);
            message.PutLL(0, dist);
            message.Send(0);
            message.Receive(0);
            source = message.GetLL(0);
         }
      }
   }
}
