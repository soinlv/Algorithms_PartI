public class PercolationStats {
    private int n, t;
    private double[] x;
    public PercolationStats(int N, int T) {
        n = N;
        t = T;
        x = new double[t];
        for (int i = 0; i < t; i++) {
           int count = 0;
           int[] list = new int[n*n];
           Percolation p = new Percolation(n);
           while (!p.percolates()) {
                int k = (int) StdRandom.uniform(1, n*n + 1);
                int r = (k - 1) / n + 1; 
                int c = (k - 1) % n + 1;
                boolean isopen = false;
                for (int q = 0; q < count; q++) {
                    if (k == list[q]) {
                        isopen = true;
                        break;
                    }
                }
                if (!isopen) {              
                    list[count] = k;
                    p.open(r, c);
                    count++;
                } 
           }
           x[i] = count * 1.0/ (n*n);
        }
    }
    public double mean() {
        return StdStats.mean(x);
    }
    public double stddev() {
        return StdStats.stddev(x);
    }
    public double confidenceLo() {
        double mean = mean();
        double std = stddev();
        return mean - 1.96*std/Math.sqrt(t);
    }
    public double confidenceHi() {
         double mean = mean();
        double std = stddev();
        return mean + 1.96*std/Math.sqrt(t);
    }
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int time = Integer.parseInt(args[1]);
        PercolationStats per = new PercolationStats(size, time);
        System.out.println("mean                    = " + per.mean());
        System.out.println("stddev                  = " + per.stddev());
        System.out.println("95% confidence interval = " + per.confidenceLo()
                               + ", " + per.confidenceHi());
    }
}
