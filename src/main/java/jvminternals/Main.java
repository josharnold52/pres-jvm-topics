package jvminternals;

public class Main {

   public static volatile int x;


   public interface Api {
      boolean count();
   }

   public static class Invoker implements Api {

      @Override
      public boolean count() {
         return (Main.x++) < 1000000;
      }

   }

   public static void main(String[] args) {
      final Api api1 = Redefiner.createRedefined(Api.class, Invoker.class);
      final Api api2 = Redefiner.createRedefined(Api.class, Invoker.class);
      final Api api3 = Redefiner.createRedefined(Api.class, Invoker.class);
      for (int i = 0; i < 100; i++) {
         while (api1.count() && api2.count() && api3.count()) {
         }
         Main.x = 0;
      }

      System.out.println("Done");
   }
}
