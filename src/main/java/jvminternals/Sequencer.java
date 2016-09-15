package jvminternals;

import java.util.Arrays;
import java.util.Collection;

public final class Sequencer<T> {

   @SafeVarargs
   public Sequencer(T... items) {
      this(Arrays.asList(items));
   }

   public Sequencer(Collection<T> items) {
      assert items.size() > 0;

      final Node[] nodes = items.stream().map(Node::new).toArray(Node[]::new);

      for (int i = 0; i < nodes.length; i++) {
         nodes[i].next = nodes[(i + 1) % nodes.length];
      }
      this.next = nodes[0];
   }

   @SuppressWarnings("unchecked")
   public T next() {
      final Node n = this.next;
      this.next = n.next;
      return (T)n.item;
   }

   private static class Node {

      public Node(Object item) {
         super();
         this.item = item;
      }

      final Object item;
      Node next;
   }

   private Node next;

}
