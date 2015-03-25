import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {    
    private int N;
    private Node head;
    private Node tail;
    //helper linked list class
    private class Node {
        private Item item;
        private Node left;
        private Node right;
    }
    
    public Deque() {                       // construct an empty deque
       head = null;
       tail = null;
        N = 0;
    }
    public boolean isEmpty() {             // is the deque empty?
        return N == 0;
    }  
    public int size() {  // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item) {  // add the item to the front
       if (item == null) throw new NullPointerException("the item is null"); 
       if (N == 0)
       {
           Node origin = new Node();
           origin.item = item;
           head = origin;
           tail = origin;
           N++;
       }
       else 
       {
           Node oldhead = head;
           head = new Node();
           head.item = item;
           head.right = oldhead;
           oldhead.left = head;
           N++;
       }
    } 
    public void addLast(Item item) {           // add the item to the end
        if (item == null) throw new NullPointerException("the item is null");
        if (N == 0)
        {
           Node origin = new Node();
           origin.item = item;
           head = origin;
           tail = origin;
           N++;
        }
        else
        {
            Node oldtail = tail;
            tail = new Node();
            tail.item = item;
            tail.left = oldtail;
            oldtail.right = tail;
            N++;
        }
    }
   public Item removeFirst()                // remove and return the item from the front
   {
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       Item item = head.item;
       head = head.right;
       N--;
       return item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if (isEmpty()) throw new NoSuchElementException("Deque underflow");
       Item item = tail.item;
       tail = tail.left;
       N--;
       return item;
   } 
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   private class DequeIterator implements Iterator<Item> {
       private Node current = head;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.right; 
            return item;
        }
   }
   public static void main(String[] args)   // unit testing
   {
       Deque<String> s = new Deque<String>();
       s.addFirst("a");
       s.addFirst("b");
       s.addLast("c");
       System.out.println(s.isEmpty());
       Iterator<String> i = s.iterator();
       int k = 3;
       while (k >= 0)
       {
           String e = i.next();
           StdOut.println(e);
           k--;
       }
        System.out.println(s.size());
   }
}
