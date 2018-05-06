import java.util.*;
import java.io.*;

class MessageInputStream extends InputStream {
   public int source;
   public int bCount = 0;
   public MessageInputStream(int s) { source=s; }
   @Override
   public int read() { bCount++; return message.GetChar(source); }
}

class MessageOutputStream extends OutputStream {
   public int source;
   public int bCount = 0;
   public MessageOutputStream(int s) { source=s; }
   @Override
   public void write(int b) { bCount++; message.PutChar(source, (char)b); }
}

@SuppressWarnings("unchecked")
abstract class MasterArch<M2W, W2M> {
   public abstract List<M2W> jobs();
   public abstract void respondTo(int workerId, W2M msg);
   public abstract void finish();
   public abstract W2M worker(M2W job);

   public boolean skipFirstMessage = true;

   public static void writeObj(int to, Object obj) {
      try {
         MessageOutputStream mos = new MessageOutputStream(to);
         ObjectOutputStream oos = new ObjectOutputStream(mos);
         oos.writeObject(obj);
         oos.flush();
         //System.err.println(mos.bCount+" bytes written");
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public static Object readObj(int from) {
      try {
         MessageInputStream mis = new MessageInputStream(from);
         ObjectInputStream ois = new ObjectInputStream(mis);
         //System.err.println(mos.bCount+" bytes read");
         return ois.readObject();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

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

   public void assignJobFromQueue(Collection<M2W> queue, int workerId) {
      if (queue.size() > 0) {
         M2W job = queue.iterator().next();
         queue.remove(job);

         message.PutChar(workerId, 'c');
         writeObj(workerId, job);
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
      List<M2W> jobList = jobs();
      int jobsToComplete = jobList.size();
      HashSet<M2W> jobQueue = null;

      if (skipFirstMessage) {
         jobList.subList(0, Math.min(numWorkers(), jobList.size())).clear();
      }

      jobQueue = new HashSet<M2W>(jobList);

      if (!skipFirstMessage) {
         for (int node = 1; node < message.NumberOfNodes(); node++) {
            assignJobFromQueue(jobQueue, node);
         }
      }


      for ( ; jobsToComplete > 0; jobsToComplete--) {
         int node = message.Receive(-1);
         W2M resp = (W2M)readObj(node);
         respondTo(node, resp);
         assignJobFromQueue(jobQueue, node);
      }

      finish();
   }

   public void runWorker() {
      M2W msg = null;
      if (skipFirstMessage) {
         List<M2W> j = jobs();
         if (message.MyNodeId()-1 >= j.size()) {
            return;
         }
         msg = j.get(message.MyNodeId()-1);
      } else {
         message.Receive(0);
      }

      while (msg != null || message.GetChar(0) == 'c') {
         if (msg == null) {
            msg = (M2W)readObj(0);
         }
         W2M resp = worker(msg);
         writeObj(0, resp);
         message.Send(0);
         message.Receive(0);
         msg = null;
      }
   }
}

class Range implements Serializable {
   public long start = 0, end = 0;
   public Range(long s, long e) { start=s; end=e; }
   public String toString() { return "["+start+" "+end+")"; }
}

public class Main extends MasterArch<Range, Long> {
   public static void main(String[] args) { new Main().run(); }

   // SHARD LOGIC
   public static final int NUM_PARTS = 10;//(int)(numWorkers())*3;
   public static int[] parts = partition(500, NUM_PARTS);

   public List<Range> jobs() {
      ArrayList<Range> out = new ArrayList<Range>(parts.length);
      long prev = 0;
      for (int i = 0; i < parts.length; i++) {
         int curr = parts[i];
         out.add(new Range(prev,curr));
         prev = curr;
      }
      return out;
   }


   // MASTER NODE LOGIC
   public long hash = 0;
   public void respondTo(int workerId, Long l) {
      hash += l;
   }

   public void finish() {
      System.out.println(hash);
   }


   // WORKER NODE LOGIC
   public Long worker(Range r) {
      long hash = 0;
      for (long i = r.start; i < r.end; i++) {
         hash += i;
      }
      return hash;
   }
}
