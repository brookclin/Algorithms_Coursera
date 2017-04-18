import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
   private int n;
   private Node first;
   private Node last;
   private class Node
   {
     private Item item;
     private Node next;
     private Node prev;
   }
   public Deque()                           // construct an empty deque
   {
     n = 0;
     first = null;
     last = null;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
     return n == 0;
   }
   public int size()                        // return the number of items on the deque
   {
     return n;
   }
   public void addFirst(Item item)          // add the item to the front
   {
     if (item == null)
     {  
       throw new java.lang.NullPointerException();  
     } 
     Node oldfirst = first;
     first = new Node();
     first.item = item;
     if (isEmpty())
     {
       first.next = null;
       last = first;
     }
     else
     {
       first.next = oldfirst;
       oldfirst.prev = first;
     }
     n++;
   }
   public void addLast(Item item)           // add the item to the end
   {
     if (item == null)
     {  
       throw new java.lang.NullPointerException();  
     }  
     Node oldlast = last;
     last = new Node();
     last.item = item;
     if (isEmpty())
     {
       last.prev = null;
       first = last;
     }
     else
     {
       oldlast.next = last;
       last.prev = oldlast;
     }
     n++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
     if (isEmpty()) throw new NoSuchElementException("Stack underflow");
     Item item = first.item;
     first = first.next;
     n--;
     if (isEmpty())
     {
       last = null;
       first = null;
     }
     else
     {
       first.prev = null;
     }
     return item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
     if (isEmpty()) throw new NoSuchElementException("Stack underflow");
     Item item = last.item;
     last = last.prev;
     n--;
     if (isEmpty())
     {
       last = null;
       first = null;
     }
     else
     {
       last.next = null;
     }
     return item;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
     return new ListIterator();
   }
   private class ListIterator implements Iterator<Item>
   {
     private Node current = first;
     public boolean hasNext() 
     {
       return current != null;
     }
     public void remove()
     {
       throw new UnsupportedOperationException();
     }
     public Item next()
     {
       if (!hasNext()) throw new NoSuchElementException();
       Item item = current.item;
       current = current.next;
       return item;
     }
   }
}