public class Board {
    
    private int[][] board;
    private int N;
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks    
                                           // (where blocks[i][j] = block in row i, column j)
    {
        N = blocks.length;
        //Firstly I was using board = blocks, which caused problem in 
        //later queue iteration (e.g., shows the latest element twice insted of 
        //showing both of the elements)!
        //I still don't understand why this would lead
        // to that problem.
        board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
            }
      
        }
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                if(board[i][j] != 0) {
                if(board[i][j] != i * N + j + 1) count++; 
                }
            }
        }
        return count;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                if(board[i][j] != 0) {
               int row = (board[i][j] - 1) / N;
               int col = board[i][j] - row * N - 1;
               count += Math.abs(row - i) + Math.abs(col - j);
               }
            }
        }
        return count;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        int count = this.hamming();
        return count == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        Board twin = new Board(this.board);
        int temp = 0;
        int row = 0;
        int col = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                if (twin.board[i][j] == 0) {
                  row = i;
                  col = j;
                  break;
                }
            }
        }
        if (row == N - 1)
        {
            if (col == N - 1) {
               temp = twin.board[row - 1][col];
               twin.board[row - 1][col] = twin.board[row - 1][col - 1];
               twin.board[row - 1][col - 1] = temp;
            }
            else {
               temp = twin.board[row - 1][col];
               twin.board[row - 1][col] = twin.board[row - 1][col + 1];
               twin.board[row - 1][col + 1] = temp;
            }   
        }
        else {
            if (col == N - 1) {
               temp = twin.board[row + 1][col];
               twin.board[row + 1][col] = twin.board[row + 1][col - 1];
               twin.board[row + 1][col - 1] = temp;
            }
            else {
               temp = twin.board[row + 1][col];
               twin.board[row + 1][col] = twin.board[row + 1][col + 1];
               twin.board[row + 1][col + 1] = temp;
            }   
        }
        return twin;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        boolean flag = true;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
            if (this.board[i][j] != that.board[i][j]) flag = false;
            }
        return flag;       
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board>  nb = new Queue<Board>();
        Board above = new Board(this.board);
        Board right = new Board(this.board);
        Board below = new Board(this.board);
        Board left = new Board(this.board);
        int temp = 0;
        int row = 0;
        int col = 0;
        //First find the blank block, and store its row and column numbers.
        for (int i = 0; i < N; i++) {
           for (int j = 0; j < N ; j++) {
               if (board[i][j] == 0) {
                   row = i;
                   col = j;
                   break;
               }
           }
       }
        //Check if the 4 blocks around the blank block are valid in range, if so 
        //exchange the blank block and the checked block and enqueue the new 
        //board, if not, continue.
        if ((row - 1) >= 0) {
        //exchange the checked and blank blocks and enqueue the new board
            temp = 0;
            temp = above.board[row][col];
            above.board[row][col] = above.board[row - 1][col];
            above.board[row - 1][col] = temp;
            nb.enqueue(above);
//            System.out.println(above.toString());
        }
        if ((col + 1) <= N - 1) {
        //exchange the checked and blank blocks and enqueue the new board  
            temp = 0;
            temp = right.board[row][col];
            right.board[row][col] = right.board[row][col + 1];
            right.board[row][col + 1] = temp;
            nb.enqueue(right);
//            System.out.println(right.toString());
        }
        if ((row + 1) <= N - 1) {
        //exchange the checked  and blank blocks and enqueue the new board 
            temp = 0;
            temp = below.board[row][col];
            below.board[row][col] = below.board[row + 1][col];
            below.board[row + 1][col] = temp;
            nb.enqueue(below);
//            System.out.println(below.toString());
        }
        if ((col - 1) >= 0) {
        //exchange the checked and blank blocks and enqueue the new board 
            temp = 0;
            temp = left.board[row][col];
            left.board[row][col] = left.board[row][col - 1];
            left.board[row][col - 1] = temp;
            nb.enqueue(left);
//            System.out.println(left.toString());
        }
    return nb;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
         StringBuilder s = new StringBuilder();
         s.append(N + "\n");
         for (int i = 0; i < N; i++) {
             for (int j = 0; j < N; j++) {
                 s.append(String.format("%2d ", board[i][j]));
             }
        s.append("\n");
        }
        return s.toString(); 
    }
    public static void main(String[] args) // unit tests (not graded)
    {
         for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println(initial.isGoal());
            System.out.println(initial.dimension() + " hamming: " + 
                               initial.hamming() + 
            " manhattan " + initial. manhattan());
            System.out.println(initial.toString() + "its twin: "
                                   + initial.twin());
            test neighbors() and equals()
            Queue<Board> nb = (Queue<Board>) initial.neighbors(); 
            System.out.println(initial.toString());           
            Board twin = initial.twin();
            System.out.println(twin.toString());          
            System.out.println(initial.equals(initial.twin()));
            while(!nb.isEmpty())
                 System.out.println(nb.dequeue().toString());
        }
    }
}