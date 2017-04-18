import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
   private double[] x;
   private int trials;
   public PercolationStats(int n, int trials)    
   {
     if (n <= 0) throw new IllegalArgumentException();
     if (trials <= 0) throw new IllegalArgumentException();
     this.trials = trials;
     x = new double[trials];
     for (int times = 0; times < trials; times++)
     {
       Percolation pc = new Percolation(n);
       int opened = 0;
       while (!pc.percolates())
       {
         int randomX = StdRandom.uniform(1, n+1);
         int randomY = StdRandom.uniform(1, n+1);
         if (!pc.isOpen(randomX, randomY))
         {
           pc.open(randomX, randomY);
           opened++;
         }
       }
       x[times] = (opened+0.0) / (n*n);
     }
   }
   public double mean()               
   {
     return StdStats.mean(x);
   }
   public double stddev()                       
   {
     return StdStats.stddev(x);
   }
   public double confidenceLo()                  
   {
     return (mean() - (1.96*stddev())/Math.sqrt(trials));
   }
   public double confidenceHi()                  
   {
     return (mean() + (1.96*stddev())/Math.sqrt(trials));
   }

   public static void main(String[] args)    
   {
     int a = Integer.parseInt(args[0]);
     int b = Integer.parseInt(args[1]);
     PercolationStats ps = new PercolationStats(a, b);
     StdOut.println("mean                    = " + ps.mean());
     StdOut.println("stddev                  = " + ps.stddev());
     StdOut.print("95% confidence interval = " + ps.confidenceLo() + ", ");
     StdOut.print(ps.confidenceHi());
   }
}