import java.util.Arrays;
import java.util.Stack;
import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int N;
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {                                      // (where blocks[i][j] = block in row i, column j)
      N = blocks.length;
      tiles = new int[N][];
      for (int i = 0; i < N; i++){
        tiles[i] = Arrays.copyOf(blocks[i], N);
      }
    }
    public int dimension()                 // board dimension n
    {
      return N;
    }
    public int hamming()                   // number of blocks out of place
    {
      int score = 0;
      for (int i = 0; i < N; i++){
        int nj = N;
        if (i == N-1) nj--;
        for (int j = 0; j < nj; j++){
          if (tiles[i][j] != i*N+j+1)
            score++;
        }
      }
      return score;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
      //wrong calculation
      int score = 0;
      int i = 0, j = 0;
      for (i = 0; i < N; i++){
        for (j = 0; j < N; j++){
          if (!(tiles[i][j] == 0 || tiles[i][j] == i*N+j+1))
            score += Math.abs(((tiles[i][j]-1)/N)-i) + Math.abs(((tiles[i][j]-1)%N)-j);
        }
      }
      return score;
    }
    public boolean isGoal()                // is this board the goal board?
    {
      if (hamming() == 0)
        return true;
      else
        return false;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
      int[][] twinBoard = new int[N][];
      for (int i = 0; i < N; i++){
        twinBoard[i] = Arrays.copyOf(tiles[i], N);
      }
      if ((twinBoard[0][0] == 0 || twinBoard[0][1] == 0))
        swap(twinBoard, 1, 0, 1, 1);
      else
        swap(twinBoard, 0, 0, 0, 1);
      /*int i1 = StdRandom.uniform(N);
      int j1 = StdRandom.uniform(N);
      while (twinBoard[i1][j1] == 0){
        i1 = StdRandom.uniform(N);
        j1 = StdRandom.uniform(N);
      }
      int i2 = StdRandom.uniform(N);
      int j2 = StdRandom.uniform(N);
      while (((i2 == i1) && (j2 == j1)) || twinBoard[i2][j2] == 0)
      {
        i2 = StdRandom.uniform(N);
        j2 = StdRandom.uniform(N);
      }
      int temp = twinBoard[i1][j1];
      twinBoard[i1][j1] = twinBoard[i2][j2];
      twinBoard[i2][j2] = temp;*/
      return new Board(twinBoard);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
      if (y == this) return true;
      if (y == null) return false;
      if (y.getClass() != this.getClass()) return false;
      Board that = (Board) y;
      if (this.N != that.N) return false;
      for (int i = 0; i < N; i++){
        for (int j = 0; j < N; j++){
          if (tiles[i][j] != that.tiles[i][j]) return false;
        }
      }
      return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
      Stack<Board> boardStack = new Stack<Board>();
      int i = 0, j = 0;
      int temp;
      int[][] tempBoard = new int[N][N];
      for (int a = 0; a < N; a++){
        for (int b = 0; b < N; b++){
          tempBoard[a][b] = tiles[a][b];
          if (tiles[a][b] == 0){
            i = a;
            j = b;
          }
        }
      }
      //up
      if (i != 0){
        swap(tempBoard, i-1, j, i, j);
        boardStack.push(new Board(tempBoard));
        swap(tempBoard, i-1, j, i, j);
      }
      //down
      if (i != N-1){
        swap(tempBoard, i+1, j, i, j);
        boardStack.push(new Board(tempBoard));
        swap(tempBoard, i+1, j, i, j);
      }      
      //left
      if (j != 0){
        swap(tempBoard, i, j-1, i, j);
        boardStack.push(new Board(tempBoard));
        swap(tempBoard, i, j-1, i, j);
      }
      //right
      if (j != N-1){
        swap(tempBoard, i, j+1, i, j);
        boardStack.push(new Board(tempBoard));
        swap(tempBoard, i, j+1, i, j);
      }
      return boardStack;
    }
    private void swap(int[][] array, int i, int j, int a, int b){
      int temp = array[i][j];
      array[i][j] = array[a][b];
      array[a][b] = temp;
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
      StringBuilder s = new StringBuilder();
      s.append(N + "\n");
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          s.append(String.format("%2d ", tiles[i][j]));
        }
        s.append("\n");
      }
      return s.toString();
    }
    public static void main(String[] args) // unit tests (not graded)
    {
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] tiles = new int[n][n];
      
      for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
          tiles[i][j] = in.readInt();
        }
      }
      
      Board initial = new Board(tiles);
      StdOut.printf("hamming:%d manhattan:%d \n", initial.hamming(), initial.manhattan());
      StdOut.println("dim:" + initial.dimension());
      StdOut.println(initial.toString());
      StdOut.println("goal:" + initial.isGoal());
      StdOut.println("twin:\n" + initial.twin().toString());
 
      StdOut.println("neighbours:");
 
      for (Board s : initial.neighbors()) {
        StdOut.println(s.toString());
      }
        
    }
}