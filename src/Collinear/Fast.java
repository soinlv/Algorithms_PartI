import java.util.Arrays;
public class Fast {
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
      
        for (int i = 0; i < N; i++) {
            Point p = a[i];
//            System.out.println(p);
            //To initialize a size changing auxiliary arry
            Point[] aux = new Point[N - i];
            int l = 0;
            //Assign the aux arry with the elements from i to N-1 in the 
            //original array            
            for (int n = i; n < N; n++) {
                aux[l++] = a[n];
            }
            //sort the aux array using the slope relative to the current a[i]
            Arrays.sort(aux, p.SLOPE_ORDER);
//            for (int k = i ; k <= N - 1 ; k++) {
//                System.out.println(aux[k].slopeTo(p)); 
//            }
            //find the equal adjacent points, index from 0 to N - i - 1 to agree
            //with the index in the aux array, k < N - i - 1 to prevent index
            //out of bound in the while loop
            for (int k = 0 ; k < N - i - 1 ; k++) {
                int count = 0;
                while (aux[k].slopeTo(p) == aux[k + 1].slopeTo(p)) {
                    count++;
                    k++;
                    // to prevent index out of bounds.
                    if (k == N - i - 1) break;
                }
                if (count >= 2) {
                    // create a new array containg only the points in a line
                    Point[] ad = new Point[count + 2];
                    int n = 1;
                    // add the invoking point itself
                    ad[0] = p;
                    // add the rest of points 
                    for (int m = k - count; m <= k ; m++) {
                        ad[n++] = aux[m];
                    }
                    //sort the points to avoid permutaion of the points
                    Arrays.sort(ad);
                    //check if this line is a segment of previous lines
                    boolean flag = false;
                    for(int m = 0; m < i; m++) {
                        if(ad[0].slopeTo(ad[1]) == ad[0].slopeTo(a[m])) {
                            flag = true; 
                            break;
                        }
                    }
                    //if it's a new line ,print the points and draw the lines.
                    if (!flag) {
                    ad[0].drawTo(ad[count + 1]);
                    for (int m = 0; m < count + 1; m++) {
                        System.out.print(ad[m] + " -> ");
                    }
                    System.out.println(ad[count + 1]);
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