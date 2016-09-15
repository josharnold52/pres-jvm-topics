package jvminternals.escape;

import jvminternals.Opts;

public class EscapeExample {

   public static class Worker extends Thread {
      public double l;
      public double w;

      public double area;

      public double diagonal;

      public Worker(double l, double w) {
         super();
         this.l = l;
         this.w = w;
      }

      @Override
      public void run() {
         final Rectangle rect = new Rectangle(this.l, this.w);

         this.area = rect.getArea();
         this.diagonal = rect.getDiagonalLength();
      }
   }


   public static void main(String[] args) throws Exception {

      final String someOpt = Opts.getOpt(args, "--some-opt", "defaultValue");

      double areas = 0;
      double diagonals = 0;
      for (long iters = 0;; iters++) {
         final Worker w = new Worker(2, 3);
         w.start();
         w.join();
         areas += w.area;
         diagonals += w.diagonal;

         if ((iters & 0xFFF) == 0xFFF) {
            System.err.printf("iters=%s results=%s,%s\n", iters, areas, diagonals);
            areas = 0;
            diagonals = 0;
         }
      }
   }


}
