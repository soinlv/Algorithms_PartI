/*************************************************************************
 * Name: Fan Zhang
 * Email: zhangfan19910726@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Slope_Order();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) {
            return +0.0;
        }
        if (this.y == that.y) return Double.POSITIVE_INFINITY;
        return (that.y - this.y)*1.0 / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;     
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class Slope_Order implements Comparator<Point>
    {
        public int compare (Point v, Point w)
        {
            if ((v.slopeTo(Point.this) - w.slopeTo(Point.this)) * (-1) < 0)
                return -1;
            else if ((v.slopeTo(Point.this) - w.slopeTo(Point.this)) * (-1) > 0)
                return +1;
            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point m = new Point(2,3);
        Point n = new Point(1,2);
        Point l = new Point(1,2);
        Point o = new Point(4,2);
        if(5 == 5 )
        {
            System.out.println(m.slopeTo(o));
        }
    }
}