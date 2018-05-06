import java.util.*;
import java.io.*;

interface Messageable {
   public void writeMessage(int to);
}
class MasterNode {
   public abstract Collection<? extends Messageable> jobs();
   public abstract Messageable terminalJob();
   public abstract void respondTo(int workerId);
   public abstract void finish();

   public static int[] partitionSizes(int rangeLength, int partitions) {
      int[] out = new int[partitions];
      int midPoint = (rangeLength + 1) % partitions;
      Arrays.fill(out,0,midPoint,(rangeLength + 1)/partitions + 1);
      Arrays.fill(out,midPoint,partitions,(rangeLength + 1)/partitions);
      return out;
   }

   public void assignJobFromQueue(Collection<Messageable> queue, int workerId) {
      Messageable job = null;
      if (jobs.size() > 0) {
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

      for (int node = 1; node < message.NumberOfNodes(); node++) {
         assignJobFromQueue(jobQueue, node);
      }

      for (int jobsToComplete = jobs.size(); jobsToComplete > 0; jobsToComplete--) {
         respondTo(Receive(-1));
         assignJobFromQueue(jobQueue, node);
      }

      finish();
   }
}

class ShhhhMessageable implements Messageable {
   public long index;
   public StartState(long i) { index=i; }
   public void writeMessage(int to) { message.PutLL(index); }
}

class Path {
   long source, dest, dist;
   public Path(long s, long de, long di) { source=s; dest=de; dist=di; }
}

class ShhhhMasterNode extends MasterNode {
   public static final int NUM_PARTS = shhhh.GetN()*2;
   public static int[] parts = new int[NUM_PARTS+1];
   static {
      int[] partSizes = partitionSizes(NUM_PARTS, message.NumberOfNodes()-1);

      parts[0] = 0;
      parts[1] = 1;

      int curr = 0;
      for (int i = 1; i < partSizes.length; i++) {
         parts[i+1] = curr;
         curr += partSizes[i];
      }
      System.err.println("SANITY "+curr+" "+shhh.GetN());
      System.err.println("SANITY "+Arrays.toString(parts));
   }

   public Collection<? extends Messageable> jobs() {
      HashSet<ShhhMessageable> out = new HashSet<ShhhhMessageable>(parts.length);
      out.add(new ShhhhMessageable(1));
      for (int i : parts) {
         out.add(new ShhhhMessageable(i));
      }

      return out;
   }

   public Messageable terminalJob() { return new ShhhhMessageable(-1); }

   public HashMap<Long,Path> paths = new HashMap<Long,Path>(jobs().size());
   public void respondTo(int workerId) {
      long source = GetLL(child);
      long dest = GetLL(child);
      long dist = GetLL(child);
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
               curr = GetLeft(curr);
               dist++;
            } while (Arrays.binarySearch(ShhhhMessageable.parts, curr) > 0);

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
