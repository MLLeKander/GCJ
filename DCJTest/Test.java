import java.util.*;
import java.lang.reflect.*;

public class Test {
   private static Field[] orderedFields(Class<?> c) {
      ArrayList<Field> fields = new ArrayList<Field>();
      for (Field f : c.getDeclaredFields()) {
         Class<?> type = f.getType();
         if (!Modifier.isStatic(f.getModifiers()) &&
               (type.equals(char.class) || type.equals(int.class) || type.equals(long.class))) {
            fields.add(f);
         }
      }

      Field[] fs = fields.toArray(new Field[fields.size()]);

      Arrays.sort(fs, new Comparator<Field>() {
         @Override
         public int compare(Field a, Field b) {
            return a.getName().compareTo(b.getName());
         }
      });
      return fs;
   }

   public static void main(String[] args) throws Exception {
      Integer i = new Integer(1);

      for (Field f : orderedFields(i.getClass())) {
         System.out.println(f);
         f.setInt(i, 10);
         System.out.println(i);
      }
   }
}
