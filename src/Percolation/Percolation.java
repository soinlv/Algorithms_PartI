public class Percolation {
        private int[][] g;
        private int[][] flag;
        private WeightedQuickUnionUF wuf;
        private int n;
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) { 
        if (N <= 0) {
            throw new IllegalArgumentException("index " + N + " is negative");
        }
        //2 additional sites represent the virtural top and 
        //bottom sites, N*N and N*N+1.
        g = new int[N][N];
        flag = new int[N][N];
        int i, j;
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                g[i][j] = i*N + j;
                flag[i][j] = 0;
            }
        }
        wuf = new WeightedQuickUnionUF(N*N +2);
        n = N;
        //connect all first row sites to site N*N+1, all bottom sites to site N*N+2;
        for (j = 0; j < N; j++) {
            wuf.union(N*N , g[0][j]);
            wuf.union(N*N + 1, g[N - 1][j]);
        }
    }
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) { 
        int x = i - 1;
        int y = j - 1;
        validate(x, y);
        if (flag[x][y] == 0)
        {   flag[x][y] = 1;
            if (x - 1 >= 0) 
                if (isOpen(x , y + 1))
                    wuf.union(g[x][y], g[x - 1][y]);
            if (x + 1 <= n - 1) 
                if (isOpen(x + 2, y + 1))
                    wuf.union(g[x][y], g[x + 1][y]);
            if (y - 1 >= 0) 
                if (isOpen(x + 1, y))
                    wuf.union(g[x][y], g[x][y - 1]);
            if (y + 1 <= n - 1) 
                if (isOpen(x + 1, y + 2))
                    wuf.union(g[x][y], g[x][y + 1]);
    }
    }
    // is site (row i, column j) open?
   public boolean isOpen(int i, int j) {
        validate(i - 1, j - 1);
        if (flag[i - 1][j - 1] == 1)
            return true;
        return false;
    }
    // is site (row i, column j) full ?
  
    public boolean isFull(int i, int j) { 
       if (wuf.connected(g[i - 1][j - 1], n*n))
           return true;
       return false;
    }
    // does the system percolate?
    public boolean percolates() {   
        if (wuf.connected(n*n , n*n + 1))
            return true;
        return false;
    }
    
    private void validate(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            System.out.println(i);
            System.out.println(j);
            throw new IndexOutOfBoundsException("index is not between 1 and "
                                                    + n);
        } 
    }
    // test client (optional)
//    public static void main(String[] args) {
//        int count = 0;
//        Percolation p = new Percolation(5);
//        while(!p.percolates()){
//                int k = (int) StdRandom.uniform(0, 5*5);
//                int i = k/5; int j = k%5;
//                p.open(i,j);
//        }
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                if(p.flag[i][j] == 0)
//                    count++;
//            }
//        }
//        System.out.println(count*1.0/(5*5));
//    }
}