package jvminternals.optovolatile;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

import jvminternals.Sequencer;

public class OptoVolatile {

   public static long normalField;

   public static volatile long volatileField;

   public static class Worker extends Thread {

      public Worker(LongSupplier supplier) {
         super();
         this.supplier = supplier;
      }

      @Override
      public void run() {
         final long start = System.nanoTime();
         long accumulator = 0;
         for (long c = 0; c < 100L * 1000L * 1000L; c++) {
            accumulator += this.supplier.getAsLong();
         }
         this.result = accumulator;
         this.elapsed = System.nanoTime() - start;
      }

      public LongSupplier supplier;
      public long result;
      public long elapsed;

   }

   public static final class Normal1 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.normalField;
      }
   }

   public static final class Normal2 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.normalField;
      }
   }

   public static final class Normal3 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.normalField;
      }
   }


   public static final class Volatile1 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.volatileField;
      }
   }

   public static final class Volatile2 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.volatileField;
      }
   }

   public static final class Volatile3 implements LongSupplier {
      @Override
      public long getAsLong() {
         return OptoVolatile.volatileField;
      }
   }


   public static void main(String[] args) throws Exception {

      OptoVolatile.normalField = 1;
      OptoVolatile.volatileField = 2;

      final Sequencer<LongSupplier> impls =
         OptoVolatile.getImpls(args.length == 0 ? 0 : Integer.parseInt(args[0]));

      for (;;) {
         final Worker w = new Worker(impls.next());
         w.start();
         w.join();
         System.out.printf("%s %s\n", w.result, TimeUnit.NANOSECONDS.toMillis(w.elapsed));
      }
   }

   private static final Sequencer<LongSupplier> getImpls(int x) {
      switch (x % 4) {
         case 0:
            System.err.println("1 Normal Implementation (Monomorphic)");
            return new Sequencer<>(new Normal1(), new Normal1(), new Normal1());
         case 1:
            System.err.println("1 Volatile Implementation (Monomorphic)");
            return new Sequencer<>(new Volatile1(), new Volatile1(), new Volatile1());
         case 2:
            System.err.println("3 Normal Implementations (Megamorphic)");
            return new Sequencer<>(new Normal1(), new Normal2(), new Normal3());
         case 3:
            System.err.println("3 Volatile Implementations (Megamorphic)");
            return new Sequencer<>(new Volatile1(), new Volatile2(), new Volatile3());
      }
      throw new AssertionError();
   }


}
