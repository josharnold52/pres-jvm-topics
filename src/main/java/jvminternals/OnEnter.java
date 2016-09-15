package jvminternals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OnEnter {


   public static void run(final Runnable action) {
      class Rdr extends Thread {
         @Override
         public void run() {
            try {
               final BufferedReader rdr =
                  new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
               while ((rdr.readLine() != null)) {
                  action.run();
               }
            }
            catch (final IOException e) {
               // ignore
            }
         }
      }

      final Rdr r = new Rdr();
      r.setDaemon(true);
      r.start();

   }
}
