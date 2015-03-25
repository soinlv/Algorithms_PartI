public class Brute {
    public static void main(String[] args) {
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] a = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            a[i] = p;
            p.draw();
        }
        for (int s = 0; s < N; s++) {
            for (int r = 0; r < N; r++) {
                for (int q = 0; q < N; q++) {
                    for (int p = 0; p < N; p++){
                        if (a[p].slopeTo(a[q]) == a[p].slopeTo(a[r]) & 
                            a[p].slopeTo(a[q]) == a[p].slopeTo(a[s]) & 
                            a[p].slopeTo(a[q]) != Double.NEGATIVE_INFINITY){
                            if(a[p].compareTo(a[q]) == -1 & a[q].compareTo(a[r])
                                   == -1 & a[r].compareTo(a[s]) == -1) {
                                System.out.println(a[p] + " -> " + a[q] + " -> " 
                                                       + a[r] + " -> " + a[s]);
                                a[p].drawTo(a[s]);
                                System.out.println(a[p].slopeTo(a[q]) + " " +
                                a[p].slopeTo(a[r]) + " " + a[p].slopeTo(a[s]));
                            }
                        }
                    }
                }  
            }
        }
        // display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }
}