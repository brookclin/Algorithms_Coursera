import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
   private SET<Point2D> set;
   public PointSET()                               // construct an empty set of points 
   {
     set = new SET<Point2D>();
   }
   public boolean isEmpty()                      // is the set empty? 
   {
     return set.isEmpty();
   }
   public int size()                         // number of points in the set 
   {
     return set.size();
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     set.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     return set.contains(p);
   }
   public void draw()                         // draw all points to standard draw 
   {
     for (Point2D p : set){
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       StdDraw.point(p.x(), p.y());
     }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
     if (rect == null)
       throw new java.lang.NullPointerException();
     SET<Point2D> rangeSet = new SET<Point2D>();
     for (Point2D p : set){
       if (rect.contains(p))
         rangeSet.add(p);
     }
     return rangeSet;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     if (set == null)
       return null;
     double minDistance = Double.MAX_VALUE;
     Point2D nearest = null;
     for (Point2D q : set){
       //if (p.equals(q))
       //  continue;
       double distance = p.distanceSquaredTo(q);
       if (distance < minDistance){
         minDistance = distance;
         nearest = q;
       }
     }
     return nearest;
   }
 //  public static void main(String[] args)                  // unit testing of the methods (optional) 
}