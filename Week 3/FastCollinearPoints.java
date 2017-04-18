import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.*;

public class FastCollinearPoints {
   private int N = 0;
   private Point[] cp;
   private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
     if (points.length >= 4){
       for (int i = 0; i < cp.length; i++){
         Point p = cp[i];
         Point q[] = new Point[cp.length-1];
         for (int j = 0; j < cp.length; j++){
           if (i > j) q[j] = cp[j];
           if (i < j) q[j-1] = cp[j];
         }
         Arrays.sort(q, p.slopeOrder());
         int count = 0;
         int index = 0;
         double tempSlope = p.slopeTo(q[0]);
       
         for (int j = 0; j < q.length; j++){
           if (Double.compare(p.slopeTo(q[j]), tempSlope) == 0){
             count++;
             continue;
           }else{
             if (count >= 3){
               if (p.compareTo(q[index]) <= 0){
                 N++;
                 ls.add(new LineSegment(p, q[j-1]));
               }
             }
             count = 1;
             index = j;
             tempSlope = p.slopeTo(q[j]);
           } 
         }
         if (count >= 3){
           if (p.compareTo(q[index]) <= 0){
             N++;
             ls.add(new LineSegment(p, q[q.length-1]));
           }
         }
       }
     }
   }
   
   public int numberOfSegments()        // the number of line segments
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
   public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
   
}