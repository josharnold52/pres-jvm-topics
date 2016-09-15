package jvminternals.escape;

import jvminternals.Opts;

public class EscapeExample {

   public static class Worker extends Thread {
      private static final boolean saveRectangle = Boolean.getBoolean("Worker.saveRectangle");
      static {
         System.err.println("saveRectangle = " + Worker.saveRectangle);
      }


      public double area;

      public double diagonal;

      public Rectangle output;

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

         if (Worker.saveRectangle) {
            this.output = rect;
         }
      }

      private final double l;
      private final double w;

   }


   public static void main(String[] args) throws Exception {

      System.setProperty("Worker.saveRectangle",
         String.valueOf(Opts.hasOpt(args, "--worker-save-rectangle")));

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
