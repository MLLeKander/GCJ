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

   public static long GetN() { return majority.GetN(); }
   public static long GetVote(long i) { return majority.GetVote(i); }

   public static long majorityCandidate() {
      long candidate = -1, count = 0;
      long i = 0;
      for (long pos = MyNodeId(); pos < GetN(); pos += NumberOfNodes(), i++) {
         long tmp = GetVote(pos);
         if (count == 0) {
            candidate = tmp;
            count = 1;
         } else if (candidate == tmp) {
            count++;
         } else {
            count--;
         }
      }

      return candidate;
   }

   public static long[] countOcc(long[] search) {
      long[] count = new long[search.length];
      for (long pos = MyNodeId(); pos < GetN(); pos += NumberOfNodes()) {
         long vote = GetVote(pos);
         for (int i = 0; i < search.length; i++) {
            if (vote == search[i]) {
               count[i]++;
               break;
            }
         }
      }
      return count;
   }

   public static void main(String[] args) {
      {
         long maj = majorityCandidate(), c = countOcc(new long[]{maj})[0];
         //System.err.println(maj+" "+c);
         PutLL(0, c > 0 && c > (GetN()/NumberOfNodes())/2 - 1 ? maj : -1);
         Send(0);
      }

      long[] list = new long[0];

      if (MyNodeId() == 0) {
         Set<Long> s = new HashSet<Long>();
         for (int node = 0; node < NumberOfNodes(); node++) {
            Receive(node);
            long tmp = GetLL(node);
            if (tmp != -1) {
               s.add(tmp);
            }
         }

         //System.err.println("Candidates: "+s);

         list = new long[s.size()];
         int i = 0;
         for (long l : s) {
            list[i++] = l;
         }

         for (int node = 1; node < NumberOfNodes(); node++) {
            PutInt(node, list.length);
            for (long l : list) {
               PutLL(node, l);
            }
            Send(node);
         }
      } else {
         Receive(0);
         list = new long[GetInt(0)];
         for (int i = 0; i < list.length; i++) {
            list[i] = GetLL(0);
         }
      }

      long[] counts = countOcc(list);

      if (MyNodeId() != 0) {
         for (long c : counts) {
            PutLL(0, c);
         }
         Send(0);
      } else {
         for (int node = 1; node < NumberOfNodes(); node++) {
            Receive(node);
            for (int i = 0; i < counts.length; i++) {
               counts[i] += GetLL(node);
            }
         }
         //System.err.println("final counts are "+Arrays.toString(counts));

         for (int i = 0; i < counts.length; i++) {
            if (counts[i] >= GetN()/2+1) {
               System.out.println(list[i]);
               return;
            }
         }
         System.out.println("NO WINNER");
      }
   }
}
