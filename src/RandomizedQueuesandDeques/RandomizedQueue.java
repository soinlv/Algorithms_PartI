import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
   private int N;
   private Node<Item> head;
   private Node<Item> tail;
   // helper linked list class
   private static class Node<Item> {
       private Item item;
       private Node<Item> left;
       private Node<Item> right;
   }
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       head = null;
       tail = null;
       N = 0;
   }
   public boolean isEmpty()                 // is the queue empty?
   {
       return N == 0;
   }
   public int size()                        // return the number of items on the queue
   {
       return N;
   }
   public void enqueue(Item item)           // add the item
   {
       Node<Item> oldtail = tail;
       tail = new Node<Item>();
       tail.item = item;
       tail.left = null;
       if (isEmpty()) head = tail;
       else   
       {
           oldtail.left = tail;
           tail.right = oldtail;
       }
       N++;
   }
   public Item dequeue()                    // remove and return a random item
   {
       int r = StdRandom.uniform(N);
       Node<Item> current = head; 
       while (r > 0) {
           current = current.left;
           r--;
       }
       if (r == 0) head = head.left;
       else if (r == N - 1) tail = tail.right;
       else {
           current.right.left = current.left;
       }
       N--;
       return current.item;
   }
   public Item sample()                     // return (but do not remove) a random item
   {
       int r = StdRandom.uniform(N);
       Node<Item> current = head; 
       while (r > 0) {
           current = current.left;
           r--;
       }
       return current.item;
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedIterator<Item> (head);
   }
   private class RandomizedIterator<Item> implements Iterator<Item> {
        private Node<Item> h;
        private int[] shuffle = new int[N];   
        private int count;
        public RandomizedIterator(Node<Item> head) {
            h = head;
            for (int i = 0; i < N; i++)
                shuffle[i] = i;
            StdRandom.shuffle(shuffle);
            count = 0;
        }
        public boolean hasNext()  { return count <= N - 1;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int r = shuffle[count];
            Node<Item> current = h; 
            while (r > 0) {
            current = current.left;
            r--;
         }
            count++;
            return current.item;
        }
   }
   public static void main(String[] args)   // unit testing
   {
       RandomizedQueue<String> rq = new RandomizedQueue<String>();
       rq.enqueue("a");
       rq.enqueue("b");
       rq.enqueue("c");
       rq.enqueue("d");
       Iterator<String> i = rq.iterator();
       int k = 3;
       while (k >= 0)
       {
           String e = i.next();
           StdOut.println(e);
           k--;
       }
   }
}