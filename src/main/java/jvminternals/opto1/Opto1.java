package jvminternals.opto1;

import jvminternals.OnEnter;

public class Opto1 {

   public static Long value1;


   public static class Worker extends Thread {
      public long result;

      @Override
      public void run() {
         try {
            this.result = Opto1.value1.longValue();
         }
         catch (final Throwable e) {
            this.result = -1;
         }
      }


   }

   public static void main(String[] args) throws Exception {
      // final PrintStream out = new PrintStream(new FileOutputStream("/dev/null", true));
      Opto1.value1 = 1L;

      OnEnter.run(() -> {
         Opto1.value1 = Opto1.value1 != null ? null : 1L;
      });

      for (;;) {
         final Worker w = new Worker();
         w.start();
         w.join();
      }
   }

}
