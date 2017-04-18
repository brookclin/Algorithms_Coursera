import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;
  
public class BruteCollinearPoints {
   private int N = 0;
   private Point[] cp;
   private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
     if (points == null)
       throw new java.lang.NullPointerException();
     cp = new Point[points.length];
     for (int i = 0; i < points.length; i++){
       if (points[i] == null)
         throw new java.lang.NullPointerException();
       else
         cp[i] = points[i];
     }
     Arrays.sort(cp);
     for (int i = 0; i < cp.length - 1; i++){
       if (cp[i].compareTo(cp[i+1]) == 0)
         throw new java.lang.IllegalArgumentException();
     }
    // Point arrayof4[] = new Point[4];
     for (int i = 0; i < cp.length - 3; i++){
       for (int j = i+1; j < cp.length - 2; j++){
         for(int k = j+1; k < cp.length - 1; k++){
           if (cp[i].slopeTo(cp[j]) == cp[i].slopeTo(cp[k])){
             for(int l = k+1; l < cp.length; l++){
               if (cp[i].slopeTo(cp[j]) == cp[i].slopeTo(cp[l])){
                 N++;
                 ls.add(new LineSegment(cp[i], cp[l]));
               }
             }
           }
         }
       }
     }
   }

   public int numberOfSegments()                  // the number of line segments
   {
     return N;
   }
   public LineSegment[] segments()                // the line segments
   {
     LineSegment[] lineSeg = new LineSegment[N];
     for (int i = 0; i < N; i++){
       lineSeg[i] = ls.get(i);
     }
     return lineSeg;
   }
}

