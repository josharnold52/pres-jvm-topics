package jvminternals.gclru;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LruExample {


   public static void main(String[] args) {
      final int cacheSize = Integer.parseInt(LruExample.getOpt(args, "--cache-size", "0"));

      final int keySpace = Integer.parseInt(LruExample.getOpt(args, "--key-space", "200000"));

      final long reportEveryNs = TimeUnit.SECONDS
         .toNanos(Integer.parseInt(LruExample.getOpt(args, "--report-every", "1")));

      System.out.println();

      System.out.printf("--cache-size %s --key-space %s -report-evert %s\n", cacheSize, keySpace,
         TimeUnit.NANOSECONDS.toSeconds(reportEveryNs));

      System.out.println();

      final DataStore dataStore = new DataStore(cacheSize);

      long lastReportNs = System.nanoTime();

      long loads = 0;
      long accum = 0;

      final Random rand = new Random(0);

      for (;;) {
         final int key = rand.nextInt(keySpace);
         final List<String> data = dataStore.retrieve(key);
         accum += data.hashCode();
         loads += 1;
         final long now = System.nanoTime();
         if ((now - lastReportNs) > reportEveryNs) {
            final String report = String.format(
               "ElapsedMillis=%s  Loads=%s  MicrosPerLoad=%4.8f HitRate=%4.2f  Accum=%s",
               TimeUnit.NANOSECONDS.toMillis(now - lastReportNs), loads,
               1.0 * TimeUnit.NANOSECONDS.toMicros(now - lastReportNs) / loads,
               100.0 * dataStore.hits / dataStore.retrieves, accum);
            System.out.println(report);
            LruExample.reports.add(report);
            loads = 0;
            accum = 0;
            dataStore.clearStats();
            lastReportNs = System.nanoTime();

         }
      }

   }

   private static final String getOpt(String[] data, String key, String deflt) {
      final int idx = Arrays.asList(data).indexOf(key);
      if (idx >= 0 && idx < (data.length - 1)) {
         return data[idx + 1];
      }
      return deflt;
   }

   /**
    * Contains a copy of every report line that gets output from {@link #main}. These are retained
    * to ensure that we're always tenuring some data so that the GC log isn't vacuous in the absence
    * of LRU stores.
    */
   private static final ArrayList<String> reports = new ArrayList<>();
}
