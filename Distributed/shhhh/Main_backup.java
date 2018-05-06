import java.io.*;
import java.util.*;

public class Main {
   public static boolean MSG_DEBUG = false;
   public static int NumberOfNodes() { return message.NumberOfNodes(); }
   public static int MyNodeId() { return message.MyNodeId(); }
   public static void PutChar(int target, char value) {
      if (MSG_DEBUG) { System.err.println("put "+value+" to "+target+" (char)"); }
      message.PutChar(target, value);
   }
   public static void PutInt(int target, int value) {
      if (MSG_DEBUG) { System.err.println("put "+value+" to "+target+" (int)"); }
      message.PutInt(target, value);
   }
   public static void PutLL(int target, long value) {
      if (MSG_DEBUG) { System.err.println("put "+value+" to "+target+" (long)"); }
      message.PutLL(target, value);
   }
   public static void Send(int target) {
      if (MSG_DEBUG) { System.err.println("send: ("+MyNodeId()+")->"+target); }
      message.Send(target);
   }
   public static int Receive(int source) {
      int out = message.Receive(source);
      if (MSG_DEBUG) { System.err.println("recv: "+source+"->("+MyNodeId()+")"); }
      return out;
   }
   public static char GetChar(int source) { return message.GetChar(source); }
   public static int GetInt(int source) { return message.GetInt(source); }
   public static long GetLL(int source) { return message.GetLL(source); }

   public static long GetN() { return shhhh.GetN(); }
   public static long GetRight(long i) { return shhhh.GetRightNeighbour(i); }
   public static long GetLeft(long i) { return shhhh.GetLeftNeighbour(i); }

   public static long offset = GetN()/((NumberOfNodes()-1)*2);

   public static void solveTrivial() {
      long distL = 0, distR = 0;
      for (long curr = 0; curr != 1; curr = GetLeft(curr), distL++) {
      }
      for (long curr = 1; curr != 0; curr = GetLeft(curr), distR++) {
      }
      printResult(distL, distR);
   }

   public static void printResult(long distL, long distR) {
      String text = distL == distR ? "WHATEVER" : distL > distR ? "RIGHT" : "LEFT";
      System.out.println(text+" "+Math.min(distL,distR));
   }

   public static class Path {
      long source, dest, dist;
      public Path(long s, long de, long di) { source=s; dest=de; dist=di; }
      @Override
      public int hashCode() { return (int)source; }
      @Override
      public boolean equals(Object o) { 
         if (!(o instanceof Path)) {
            return false;
         }
         return ((Path)o).source == source;
      }
      public String toString() { return "("+source+"-"+dist+"-"+dest+")"; }
   }

   public static void masterLoop() {
      HashSet<Long> unvisited = new HashSet<Long>((NumberOfNodes()-1)*2+2);
      unvisited.add(1L);
      for (long i = 0; i < GetN(); i += offset) {
         unvisited.add(i);
      }
      int numPoints = unvisited.size();

      HashMap<Long,Path> paths = new HashMap<Long,Path>(numPoints);

      while (paths.size() < numPoints) {
         int child = Receive(-1);
         long source = GetLL(child);
         long dest = GetLL(child);
         long dist = GetLL(child);
         paths.put(source,new Path(source, dest, dist));

         if (unvisited.size() > 1) {
            long newSearch = unvisited.iterator().next();
            unvisited.remove(newSearch);
            PutLL(child, newSearch);
         } else if (unvisited.size() == 1) {
            unvisited.remove(unvisited.iterator().next());
            PutLL(child, -1);
         } else {
            PutLL(child, -1);
         }
         Send(child);
      }

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
      printResult(distL,distR);
   }

   public static void slaveLoop() {
      long source = MyNodeId()*offset;

      while (source >= 0) {
         long curr = source;
         long dist = 0;
         do {
            curr = GetLeft(curr);
            dist++;
         } while (curr != 1 && curr % offset != 0);
         PutLL(0, source);
         PutLL(0, curr);
         PutLL(0, dist);
         Send(0);
         Receive(0);
         source = GetLL(0);
      }
   }

   public static void main(String[] args) {
      solveTrivial();
      //if (offset < 2) {
      //   if (MyNodeId() == 0) {
      //      solveTrivial();
      //   }
      //} else if (MyNodeId() == 0) {
      //   masterLoop();
      //} else {
      //   slaveLoop();
      //}
   }
}
