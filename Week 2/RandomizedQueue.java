import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] a;
   private int n;
   public RandomizedQueue()                 // construct an empty randomized queue
   {
     a = (Item[]) new Object[2];
     n = 0;
   }
   public boolean isEmpty()                 // is the queue empty?
   {
     return n == 0;
   }
   public int size()                        // return the number of items on the queue
   {
     return n;
   }
   private void resize(int capacity)
   {
     assert capacity >= n;
     Item[] temp = (Item[]) new Object[capacity];
     for (int i = 0; i < n; i++)
     {
       temp[i] = a[i];
     }
     a = temp;
   }
   public void enqueue(Item item)           // add the item
   {
     if (item == null)
     {
       throw new java.lang.NullPointerException(); 
     }
     if (n == a.length)
       resize(2 * a.length);
     a[n++] = item;
   }
   public Item dequeue()                    // remove and return a random item
   {
     if (isEmpty()) throw new NoSuchElementException("Stack underflow");
     int index = StdRandom.uniform(n);
     Item item = a[index];
     if (index != n-1)
     {
       a[index] = a[n-1];
     }
     a[n-1] = null;
     n--;
     // shrink
     if (n > 0 && n == a.length/4)
       resize(a.length/2);
     return item;
   }
   public Item sample()                     // return (but do not remove) a random item
   {
     if (isEmpty()) throw new NoSuchElementException("Stack underflow");
     int index = StdRandom.uniform(n);
     return a[index];
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
     return new ArrayIterator();
   }
   private class ArrayIterator implements Iterator<Item>
   {
     private Item[] temp = (Item[]) new Object[a.length];
     private int tempN = n;
     public ArrayIterator()
     {
       for (int j = 0; j < a.length; j++)
         temp[j] = a[j];
     }
     public boolean hasNext()
     {
       return tempN != 0;
     }
     public void remove()
     {
       throw new UnsupportedOperationException();
     }
     public Item next()
     {
       if (!hasNext()) throw new NoSuchElementException();
       int index = StdRandom.uniform(tempN);
       Item item = temp[index];
       if (index != tempN-1)
       {
       temp[index] = temp[tempN-1];
       }
       temp[tempN-1] = null;
       tempN--;
       return item;
     }
   }
   public static void main(String[] args)   // unit testing
   {
     int a = Integer.parseInt(args[0]);
     RandomizedQueue<Integer> que = new RandomizedQueue<Integer>();
     for (int i = 1; i <= a; i++)
     {
       que.enqueue(i);
     }
     for (int k : que)
     {
       StdOut.println(k);
     }
     for (int i = 1; i <= a; i++)
     {
       int out = que.dequeue();
       StdOut.println(out);
     }
   }
}