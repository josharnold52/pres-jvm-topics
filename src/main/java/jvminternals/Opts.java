package jvminternals;

import java.util.Arrays;

public class Opts {

   public static String getOpt(String[] data, String key, String deflt) {
      final int idx = Arrays.asList(data).indexOf(key);
      if (idx >= 0 && idx < (data.length - 1)) {
         return data[idx + 1];
      }
      return deflt;
   }

   public static int getOpt(String[] data, String key, int deflt) {
      return Integer.parseInt(Opts.getOpt(data, key, String.valueOf(deflt)));
   }

   public static long getOpt(String[] data, String key, long deflt) {
      return Long.parseLong(Opts.getOpt(data, key, String.valueOf(deflt)));
   }

   public static boolean hasOpt(String[] data, String key) {
      return Arrays.asList(data).indexOf(key) >= 0;
   }

}
