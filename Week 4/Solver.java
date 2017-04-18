import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Solver {
    private final boolean solvable;
    private final int moves;
    private final GameNode finalNode;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
      MinPQ<GameNode> tree = new MinPQ<GameNode>();
      tree.insert(new GameNode(initial, null, 0, false));
      tree.insert(new GameNode(initial.twin(), null, 0, true));
      GameNode searchNode = tree.delMin();
      while(!searchNode.getBoard().isGoal() && !tree.isEmpty()){ 
        for (Board neighbor : searchNode.getBoard().neighbors()){ 
          if (searchNode.getPrevious() == null ||
              !neighbor.equals(searchNode.getPrevious().getBoard())){
            tree.insert(new GameNode(neighbor, searchNode, searchNode.getMoves()+1, searchNode.getIsTwin()));
          }        
        }
        searchNode = tree.delMin();
      }
      solvable = !(searchNode.getIsTwin());
      if (!solvable){
        moves = -1;
        finalNode = null;
      }else{
        moves = searchNode.getMoves();
        finalNode = searchNode;
      }
    }
    private class GameNode implements Comparable<GameNode>
    {
      private Board board;
      private GameNode previous;
      private int moves;
      private boolean isTwin;
      private int manhattan;
      
      public Board getBoard(){
        return this.board;
      }
      public GameNode getPrevious(){
        return this.previous;
      }
      public int getMoves(){
        return this.moves;
      }
      public boolean getIsTwin(){
        return this.isTwin;
      }
      public int getManhattan(){
        return this.manhattan;
      }
      public GameNode(Board b, GameNode prev, int moves, boolean isTwin){
        this.board = b;
        this.previous = prev;
        this.moves = moves;
        this.isTwin = isTwin;
        this.manhattan = b.manhattan();
      }
      
      public int compareTo(GameNode that){
        int score = manhattan + moves - that.getManhattan() - that.moves;
        if (score == 0){
//          int hammingScore = board.hamming() + moves - that.getBoard().hamming() - that.moves;
//          if (hammingScore == 0)
            return 0;
//          else if (hammingScore < 0)
//            return -1;
//          else
//            return 1;
        }
        else if(score < 0)
          return -1;
        else
          return 1;
      }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
      return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
      return moves;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
      if (finalNode != null){
        Stack<Board> solution = new Stack<Board>();
        GameNode iter = finalNode;
        solution.push(iter.getBoard());
        while(iter.getPrevious() != null){
          iter = iter.getPrevious();
          solution.push(iter.getBoard());
        }
        Stack<Board> solution2 = new Stack<Board>();
        while (!solution.empty()){
          solution2.push(solution.pop());
        }
        return solution2;
      }
      return null;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
      // create initial board from file
      In in = new In(args[0]);
      int n = in.readInt();
      int[][] blocks = new int[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);

      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
        StdOut.println("No solution possible");
      else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
          StdOut.println(board);
      }
    }
}