public class PointSET {
   private SET<Point2D> pointset;
   public         PointSET()                               // construct an empty set of points 
   {
       pointset = new SET<Point2D>();
   }
   public boolean isEmpty()                      // is the set empty? 
   {
       return pointset.isEmpty();
   }
   public int size()                         // number of points in the set 
   {
       return pointset.size();
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       // I didn't check if the point is already in the set because I believe 
       // it is already implemented in Treeset in java.util.
       pointset.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       return pointset.contains(p);
   }
   public void draw()                         // draw all points to standard draw 
   {
       for (Point2D point : pointset) {
           point.draw();
       }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       if (rect == null) throw new NullPointerException("called range() with a null key");
       Queue<Point2D> inrange = new Queue<Point2D>();
       for (Point2D point : pointset) {
           if (point.x() > rect.xmin() & point.x() < rect.xmax()) {
               if (point.y() > rect.ymin() & point.y() < rect.ymax())
                   inrange.enqueue(point);
           }
       }
       return inrange;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if (p == null) throw new NullPointerException("called nearest() with a null key");
       Point2D nearestnb = new Point2D(0 , 0);
       if (size() == 0)  return null;                                           
       for (Point2D point : pointset) 
       {
           if (p.distanceTo(point) < p.distanceTo(nearestnb)) {
               nearestnb = point;
           }
       }
       return nearestnb;
   }
   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
       
   }
}