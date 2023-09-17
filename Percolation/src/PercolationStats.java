import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trails must be more than 0.");
        }
        double[] data = new double[trials];
        trail(n, trials, data);
        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
        confidenceLo = mean - (1.96 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    private void trail(int n, int trails, double[] data) {
        for (int i = 0; i < trails; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                percolation.open(row, col);
            }
            data[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev);
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo + ", "
            + percolationStats.confidenceHi + "]");
    }
}
