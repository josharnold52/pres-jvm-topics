package jvminternals;

import java.util.Arrays;
import java.util.Collection;

public final class SequencerOld<T> {

   @SafeVarargs
   public SequencerOld(T... items) {
      this(Arrays.asList(items));
   }

   public SequencerOld(Collection<T> items) {
      assert items.size() > 0;
      this.items = items.toArray();
   }

   @SuppressWarnings("unchecked")
   public T next() {
      final T res = (T)this.items[this.next++];
      this.next = this.next < this.items.length ? this.next : 0;
      return res;
   }


   private int next = 0;

   private final Object[] items;
}
