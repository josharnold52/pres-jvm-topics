package jvminternals.gclru;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A simulated data store with an LRU cache
 */
public class DataStore {


   /**
    * Constructs a DataStore
    *
    * @param cacheSize the size of the LRU cache
    */
   public DataStore(int cacheSize) {
      super();
      this.cacheSize = cacheSize;
   }

   /**
    * Returns the data (a list of Strings) for the given key.
    */
   public List<String> retrieve(int key) {
      // Do a little bit of work to ensure we always allocate some memory so that our GC log isn't
      // vacuous at a 100% hit rate
      this.keyHashes += IntStream.range(0, 10)
         .mapToObj(i -> String.valueOf(key + i))
         .collect(Collectors.joining())
         .hashCode();


      List<String> result = this.cache.get(key);
      if (result == null) {
         result = this.retrieveUncached(key);
         this.cache.put(key, result);
      }
      else {
         this.hits++;
      }
      this.retrieves++;
      return result;
   }

   public void clearStats() {
      this.hits = 0;
      this.retrieves = 0;
      this.keyHashes = 0;
   }

   /**
    * Count of cache hits
    */
   public int hits;

   /**
    * Count of retrieve calls.
    */
   public int retrieves;

   /**
    * A dummy field used to ensure that calls to {@link #retrieve} always do some allocations that
    * can't be optimized out.
    */
   public int keyHashes;

   /**
    * Simulates a load for a cache miss.
    */
   private List<String> retrieveUncached(int key) {
      final int length = 50 + (key & 127);

      final ArrayList<String> result = new ArrayList<>();
      for (int i = 0; i < length; i++) {
         result.add(String.valueOf(i + key));
      }
      return Collections.unmodifiableList(result);
   }

   private final int cacheSize;

   @SuppressWarnings("serial")
   private final LinkedHashMap<Integer, List<String>> cache =
      new LinkedHashMap<Integer, List<String>>(10, 0.75f, true) {

         @Override
         protected boolean removeEldestEntry(Entry<Integer, List<String>> eldest) {
            return this.size() > DataStore.this.cacheSize;
         }

      };


}
