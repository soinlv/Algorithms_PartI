public class KdTree {
   private Node root;
   private Queue<Node> queue = new Queue<Node>();
   private int N;
   private static class Node {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
       private int orientation;
       
       public Node(Point2D point, double xmin, double ymin, double xmax, double ymax, int orientation) {
           this.p = point;
           this.rect = new RectHV(xmin, ymin, xmax, ymax);
           this.orientation = orientation;
       }
   }
   public         KdTree()                               // construct an empty set of points 
   {
       root = null;
   }
   public           boolean isEmpty()                      // is the set empty? 
   {
       return size() == 0;
   }
   public               int size()                         // number of points in the set 
   {
       return N;
   }
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       //orientation specifies the dimension to compare, 0 represents horizontal
       //1 represents vertical.
       root = insert(root, p, 0.0, 0.0, 1.0, 1.0, 0);
   }
   private Node insert(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax, int orientation) {
       double xs = xmin;
       double ys = ymin;
       double xl = xmax;
       double yl = ymax;
       if (x == null) {
           N++;
           Node node = new Node(p, xs, ys, xl, yl, orientation);
           queue.enqueue(node);
           return node;        
       }
       if (orientation == 0) {
           if (p.x() < x.p.x()) {
               xl = x.p.x();
               x.lb = insert(x.lb, p, xs, ys, xl, yl, 1);
           }
           else {
               xs = x.p.x();
               x.rt = insert(x.rt, p, xs, ys, xl, yl, 1);
           }
       }
       else if (orientation == 1) {
           if (p.y() < x.p.y()) {
               yl = x.p.y();
               x.lb = insert(x.lb, p, xs, ys, xl, yl, 0);
           }
           else {
               ys = x.p.y();
               x.rt = insert(x.rt, p, xs, ys, xl, yl, 0);
           }
       }  
       return x;
   }
   public           boolean contains(Point2D p)            // does the set contain point p? 
   {
       return get(root, p , 0) != null;
   }
   private Point2D get(Node x, Point2D p, int orientation) {
       if (x == null) return null;
       if (orientation == 0) {
           if (p.equals(x.p)) return p;
           if (p.x() < x.p.x()) return get(x.lb, p, 1);
           else return get(x.rt, p, 1);
       }
       if (orientation == 1) {
           if (p.equals(x.p)) return p;
           if (p.y() < x.p.y()) return get(x.lb, p, 0);
           else return get(x.rt, p, 0);
       }
       return p;
   }
   public              void draw()                         // draw all points to standard draw 
   {
       for (Node node : queue) {
               System.out.println("xmin-> " + node.rect.xmin() + "xmax->  " + node.rect.xmax() 
                                      + "ymin->  " + node.rect.ymin() + "ymax->  " + node.rect.ymax());
               if (node.orientation == 0) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius(.01);
                    node.p.draw();
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.setPenRadius();
                    StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
               }
               else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius(.01);
                    node.p.draw();
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.setPenRadius();
                    StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y()); 
               }
       }
       
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       Queue<Point2D> inrect = new Queue<Point2D>();
       Queue<Node> queue = new Queue<Node>();
       queue.enqueue(root);
       while (!queue.isEmpty()) {
           Node x = queue.dequeue();
           if (x == null) continue;
           if (x.rect.intersects(rect)) {
               if (rect.contains(x.p)) 
                   inrect.enqueue(x.p);
               queue.enqueue(x.lb);
               queue.enqueue(x.rt);
           }
       }
       return inrect;
   }
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       Point2D nearestnb = root.p;
       double minDistance = p.distanceTo(nearestnb);
       Queue<Node> queue = new Queue<Node>();
       queue.enqueue(root);
       while (!queue.isEmpty()) {
           Node x = queue.dequeue();
           if (x == null) continue;
           if (x.p.distanceTo(p) < minDistance) {
               nearestnb = x.p;
               minDistance = x.p.distanceTo(p);
           }
           if (x.lb != null & x.rt != null) {
           if (x.lb.rect.distanceTo(p) < minDistance & x.rt.rect.distanceTo(p)
                  < minDistance) {
               if (x.orientation == 0) {
                   if (p.x() <= x.p.x()) {
                       queue.enqueue(x.lb);
                       queue.enqueue(x.rt);
                   }
                   else {
                       queue.enqueue(x.rt);
                       queue.enqueue(x.lb);
                   }
               }
               else {
                   if (p.y() <= x.p.y()) {
                       queue.enqueue(x.lb);
                       queue.enqueue(x.rt);
                   }
                   else {
                       queue.enqueue(x.rt);
                       queue.enqueue(x.lb);
                   }
               }
           }
           else if (x.lb.rect.distanceTo(p) < minDistance) 
               queue.enqueue(x.lb);
           else if (x.rt.rect.distanceTo(p)
                  < minDistance)
               queue.enqueue(x.rt);     
       }
           else if (x.lb != null) {
               if (x.lb.rect.distanceTo(p) < minDistance) 
               queue.enqueue(x.lb);
           }
           else if (x.rt != null) {
               if (x.rt.rect.distanceTo(p)
                  < minDistance)
               queue.enqueue(x.rt); 
           }
       }
       return nearestnb;
   }

//   public static void main(String[] args)                  // unit testing of the methods (optional) 
//   {
//        String filename = args[0];
//        In in = new In(filename);
//        KdTree kdtree = new KdTree();
//        while (!in.isEmpty()) {
//           double x = in.readDouble();
//           double y = in.readDouble();
//           Point2D p = new Point2D(x, y);
//           kdtree.insert(p);
//        }
//        kdtree.draw();
//      }     
}