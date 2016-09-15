package jvminternals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.function.Predicate;

public class Redefiner {

   public static <T> T createRedefined(Class<T> apiType, Class<? extends T> implType) {
      final Loader l = new Loader(implType.getClassLoader(), implType.getName()::equals);

      try {
         final Class<?> redefined = Class.forName(implType.getName(), true, l);
         final Constructor<?> cons = redefined.getDeclaredConstructor();
         cons.setAccessible(true);
         return apiType.cast(cons.newInstance());

      }
      catch (final ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             IllegalAccessException | InstantiationException e)
      {
         throw new RuntimeException(e);
      }

   }


   private static class DummyLoader extends ClassLoader {

      public DummyLoader(ClassLoader parent) {
         super(parent);
         // TODO Auto-generated constructor stub
      }

      @Override
      protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
         // TODO Auto-generated method stub
         return super.loadClass(name, resolve);
      }


   }

   private static class Loader extends ClassLoader {
      private final Predicate<String> shouldReload;
      private final DummyLoader sourceLoader;

      public Loader(ClassLoader sourceLoader, Predicate<String> shouldReload) {
         super(null);
         this.sourceLoader = new DummyLoader(sourceLoader);
         this.shouldReload = shouldReload;
      }

      @Override
      protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
         if (!this.shouldReload.test(name)) {
            return this.sourceLoader.loadClass(name, resolve);
         }

         final Class<?> chk = this.findLoadedClass(name);
         if (chk != null) {
            return chk;
         }

         byte[] classBytes;
         try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final byte[] buf = new byte[2048];
            final InputStream in =
               this.sourceLoader.getResourceAsStream(name.replace('.', '/') + ".class");
            for (int len; (len = in.read(buf)) > 0;) {
               out.write(buf, 0, len);
            }
            classBytes = out.toByteArray();
         }
         catch (final IOException e) {
            throw new ClassNotFoundException(name, e);
         }

         final Class<?> res = this.defineClass(name, classBytes, 0, classBytes.length);
         if (resolve) {
            this.resolveClass(res);
         }
         return res;
      }

      @Override
      public URL getResource(String name) {
         return this.sourceLoader.getResource(name);
      }

      @Override
      public Enumeration<URL> getResources(String name) throws IOException {
         return this.sourceLoader.getResources(name);
      }

      @Override
      public InputStream getResourceAsStream(String name) {
         return this.sourceLoader.getResourceAsStream(name);
      }


   }

}
