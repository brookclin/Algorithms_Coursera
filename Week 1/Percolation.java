import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private int n;
   private boolean[] openSite;
   private WeightedQuickUnionUF uf;
   public Percolation(int n)               
   {
     if (n <= 0) throw new IllegalArgumentException();
     this.n = n;
     uf = new WeightedQuickUnionUF(n*(n+2)+2);
     openSite = new boolean[n*(n+2)+2];
     for (int i = 1; i <= n; i++)
     {
       uf.union(xyTo1D(0, 1), xyTo1D(0, i));
       openSite[xyTo1D(0, i)] = true;
       uf.union(xyTo1D(n+1, 1), xyTo1D(n+1, i));
       openSite[xyTo1D(n+1, i)] = true;
     }
   }
   private int xyTo1D(int i, int j)
   {
     return i * n + j;
   }
   private void isValid(int i, int j)
   {
     if (i <= 0 || i > n) 
       throw new IllegalArgumentException();
     if (j <= 0 || j > n) 
       throw new IllegalArgumentException();
   }
   public void open(int i, int j)          
   {
     isValid(i, j);
     int xy = this.xyTo1D(i, j);
     if (this.isOpen(i, j))
     {
       return;
     }
     openSite[xy] = true;
     if (openSite[xyTo1D(i-1, j)])
     {
       uf.union(xy, xyTo1D(i-1, j));
     }
     if (openSite[xyTo1D(i+1, j)])
     {
       uf.union(xy, xyTo1D(i+1, j));
     }
     if (j != 1 && openSite[xyTo1D(i, j-1)])
     {
       uf.union(xy, xyTo1D(i, j-1));
     }
     if (j != n && openSite[xyTo1D(i, j+1)])
     {
       uf.union(xy, xyTo1D(i, j+1));
     }
   }
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
     isValid(i, j);
     return openSite[xyTo1D(i, j)];
   }
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {
     isValid(i, j);
     return isOpen(i, j) && uf.connected(xyTo1D(i, j), xyTo1D(0, 1));
   }
   public boolean percolates()             // does the system percolate?
   {
     return uf.connected(xyTo1D(0, 1), xyTo1D(n+1, 1));
   }
   
}