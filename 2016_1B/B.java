import java.util.*;

class B {
   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      int T = scan.nextInt();
      scan.nextLine();
      for (int i = 1; i <= T; i++) {
         System.out.printf("Case #%d: %s\n", i, solve(scan));
      }
   }

   public static Object solve(Scanner scan) {
      char[] c = scan.next().toCharArray(), j = scan.next().toCharArray();
      boolean misMatch = false, cBigger = false;

      int i = 0;
      for ( ; i < c.length && !misMatch; i++) {
         if (c[i] == '?' && j[i] != '?') {
            c[i] = j[i];
         } else if (j[i] == '?' && c[i] != '?') {
            j[i] = c[i];
         } else if (c[i] == '?' && j[i] == '?') {
            c[i] = j[i] = '0';
         } else if (c[i] != j[i]) {
            misMatch = true;
            cBigger = c[i] > j[i];
         }
      }
      for ( ; i < c.length; i++) {
         if (c[i] == '?') c[i] = cBigger ? '0' : '9';
         if (j[i] == '?') j[i] = cBigger ? '9' : '0';
      }

      return new String(c)+" "+new String(j);
   }
}
