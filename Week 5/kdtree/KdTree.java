import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

public class KdTree {
   private Node root;
   private int N;
   private final RectHV unitRect = new RectHV(0, 0, 1, 1);
   private static class Node {
     private Point2D p;      // the point
     private RectHV rect;    // the axis-aligned rectangle corresponding to this node
     private Node lb;        // the left/bottom subtree
     private Node rt;        // the right/top subtree
 
     public Node(Point2D p, RectHV rect){
       this.p = p;
       this.rect = rect;
     }
   }
   public KdTree(){
     N = 0;
   }
   public boolean isEmpty()                      // is the set empty? 
   {
     return (root == null);
   }
   public int size()                         // number of points in the set 
   {
     return N;
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     root = insert(root, p, unitRect, true);
     //size? duplicate input?
   }
   private Node insert(Node node, Point2D p, RectHV rect, boolean isHorizon){
     if (node == null){
       N++;
       return new Node(p, rect);
       //rectangle
     }
     double cmp;
     if (isHorizon == true){
       cmp = p.x() - node.p.x();
       if (cmp < 0) 
         node.lb = insert(node.lb, p, new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax()), !isHorizon);
       else if (cmp > 0) 
         node.rt = insert(node.rt, p, new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax()), !isHorizon);
       else{
         if (!p.equals(node.p))
           node.rt = insert(node.rt, p, new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax()), !isHorizon);
         }
     }
     else{
       cmp = p.y() - node.p.y();
       if (cmp < 0) 
         node.lb = insert(node.lb, p, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y()), !isHorizon);
       else if (cmp > 0) 
         node.rt = insert(node.rt, p, new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax()), !isHorizon);
       else{
         if (!p.equals(node.p))
           node.rt = insert(node.rt, p, new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax()), !isHorizon);
         }
     }
     /*  
     if (cmp < 0) node.lb = insert(node.lb, p, !isHorizon);
     else if (cmp > 0) node.rt = insert(node.rt, p, !isHorizon);
     else{
       if (!p.equals(node.p))
         node.rt = insert(node.rt, p, !isHorizon);
     }*/
     return node; //duplicate node
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     return contains(root, p, true);
   }
   private boolean contains(Node node, Point2D p, boolean isHorizon){
     if (node == null) return false;
     if (p.equals(node.p)) return true;
     double cmp;
     if (isHorizon == true){
       cmp = p.x() - node.p.x();
     }else{
       cmp = p.y() - node.p.y();
     }
     if (cmp < 0) return contains(node.lb, p, !isHorizon);
     else return contains(node.rt, p, !isHorizon);
   }
   public void draw()                         // draw all points to standard draw 
   {
     draw(root, true);
   }
   private void draw(Node node, boolean isHorizon){
     if (node == null) return;
     if (isHorizon == true){
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       StdDraw.point(node.p.x(), node.p.y());
       StdDraw.setPenColor(StdDraw.RED);
       StdDraw.setPenRadius();
       StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
     }
     else{
       StdDraw.setPenColor(StdDraw.BLACK);
       StdDraw.setPenRadius(0.01);
       StdDraw.point(node.p.x(), node.p.y());
       StdDraw.setPenColor(StdDraw.BLUE);
       StdDraw.setPenRadius();
       StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
     }
     draw(node.lb, !isHorizon);
     draw(node.rt, !isHorizon);
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
     if (rect == null)
       throw new java.lang.NullPointerException();
     SET<Point2D> rangeSet = new SET<Point2D>();
     range(root, rect, rangeSet);
     return rangeSet;
   }
   private void range(Node node, RectHV rect, SET<Point2D> rangeSet){
     if (node == null) return;
     if (rect.contains(node.p))
       rangeSet.add(node.p);
     if (node.lb != null && rect.intersects(node.lb.rect))
       range(node.lb, rect, rangeSet);
     if (node.rt != null && rect.intersects(node.rt.rect))
       range(node.rt, rect, rangeSet);
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
     if (p == null)
       throw new java.lang.NullPointerException();
     if (root != null) 
       return nearest(root, p, root.p);
     return null;
   }
   private Point2D nearest(Node node, Point2D p, Point2D near){
     Point2D currentNear = near;
     if (node == null)
       return near;
     double nearDist = p.distanceSquaredTo(near);        // current nearest distance
     
     if (node.lb != null && node.lb.rect.contains(p)){   // if rect of left tree contains p
       double nextDist = p.distanceSquaredTo(node.lb.p);
       if (nextDist < nearDist)
         currentNear = nearest(node.lb, p, node.lb.p);   // change nearest point
       else
         currentNear = nearest(node.lb, p, near);
       nearDist = p.distanceSquaredTo(currentNear);
       near = currentNear;
       if (node.rt != null && nearDist >= node.rt.rect.distanceSquaredTo(p)){ // if nearDist is larger than p to right tree's rect
         nextDist = p.distanceSquaredTo(node.rt.p);       // go into right tree
         if (nextDist < nearDist)
           currentNear = nearest(node.rt, p, node.rt.p);
         else
           currentNear = nearest(node.rt, p, currentNear);
       }
     }
     else if (node.rt != null && node.rt.rect.contains(p)){
       double nextDist = p.distanceSquaredTo(node.rt.p);
       if (nextDist < nearDist)
         currentNear = nearest(node.rt, p, node.rt.p);
       else
         currentNear = nearest(node.rt, p, near);
       nearDist = p.distanceSquaredTo(currentNear);
       near = currentNear;
       if (node.lb != null && nearDist >= node.lb.rect.distanceSquaredTo(p)){
         nextDist = p.distanceSquaredTo(node.lb.p);
         if (nextDist < nearDist)
           currentNear = nearest(node.lb, p, node.lb.p);   
         else
           currentNear = nearest(node.lb, p, currentNear);
       }
     }
     return currentNear;
   }
   /*public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
     /*KdTree tree = new KdTree();
     tree.insert(new Point2D(0.195080, 0.938777));
     tree.insert(new Point2D(0.351415, 0.017802));
     tree.insert(new Point2D(0.556719, 0.841373));
     tree.insert(new Point2D(0.183384, 0.636701));
     tree.insert(new Point2D(0.649952, 0.237188));
     
     StdOut.println(tree.contains(new Point2D(0.6, 0.2)));
     tree.draw();
   }*/
}