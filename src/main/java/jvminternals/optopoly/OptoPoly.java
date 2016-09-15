package jvminternals.optopoly;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongSupplier;

import jvminternals.OnEnter;
import jvminternals.Sequencer;

public class OptoPoly {

   /** Rotates through a fixed list of LongSupplier instances */
   public static volatile Sequencer<LongSupplier> instances;


   public static class Worker extends Thread {
      public long result;
      public long elapsed;

      @Override
      public void run() {
         final Sequencer<LongSupplier> sequencer = OptoPoly.instances;
         final long start = System.nanoTime();
         long accumulator = 0;
         for (long c = 0; c < 100L * 1000L * 1000L; c++) {
            accumulator += sequencer.next().getAsLong();
         }
         this.result = accumulator;
         this.elapsed = System.nanoTime() - start;
      }
   }

   public static final class Impl1 implements LongSupplier {
      @Override
      public long getAsLong() {
         return 1;
      }
   }

   public static final class Impl2 implements LongSupplier {
      @Override
      public long getAsLong() {
         return 2;
      }
   }

   public static final class Impl3 implements LongSupplier {
      @Override
      public long getAsLong() {
         return 3;
      }
   }

   public static final class Impl4 implements LongSupplier {
      @Override
      public long getAsLong() {
         return 4;
      }
   }

   public static void main(String[] args) throws Exception {
      final AtomicInteger counter = new AtomicInteger();
      OptoPoly.setSequence(0);

      OnEnter.run(() -> OptoPoly.setSequence(counter.incrementAndGet()));

      for (;;) {
         final Worker w = new Worker();
         w.start();
         w.join();
         System.out.printf("%s %s\n", w.result, TimeUnit.NANOSECONDS.toMillis(w.elapsed));
      }
   }

   private static void setSequence(int seqId) {
      switch (seqId % 4) {
         case 0:
            System.err.println("1 Implementation (Monomorphic)");
            OptoPoly.instances =
               new Sequencer<>(new Impl1(), new Impl1(), new Impl1(), new Impl1());
            break;
         case 1:
            System.err.println("2 Implementations (Bimorphic)");
            OptoPoly.instances =
               new Sequencer<>(new Impl1(), new Impl2(), new Impl1(), new Impl2());
            break;
         case 2:
            System.err.println("3 Implementations (Megamorphic)");
            OptoPoly.instances =
               new Sequencer<>(new Impl1(), new Impl2(), new Impl3(), new Impl1());
            break;
         case 3:
            System.err.println("4 Implementations (Megamorphic)");
            OptoPoly.instances =
               new Sequencer<>(new Impl1(), new Impl2(), new Impl3(), new Impl4());
            break;
      }
   }


}
