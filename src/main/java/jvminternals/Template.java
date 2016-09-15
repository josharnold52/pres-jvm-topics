package jvminternals;

public class Template {
   public static class Worker extends Thread {
      public long result;

      @Override
      public void run() {
         this.result = Thread.currentThread().getId();
      }
   }


   public static void main(String[] args) throws Exception {

      final String someOpt = Opts.getOpt(args, "--some-opt", "defaultValue");

      long results = 0;
      for (long iters = 0;; iters++) {
         final Worker w = new Worker();
         w.start();
         w.join();
         results += w.result;
         if ((iters & 0xFFFFF) == 0xFFFFF) {
            System.err.printf("iters=%s results=%s\n", iters, results);
         }
      }
   }
}
