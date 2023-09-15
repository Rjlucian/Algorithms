import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private int openNum;
    private final boolean[] opened;
    private final WeightedQuickUnionUF percolation;
    private final WeightedQuickUnionUF full;
    // 0 -> up; 1 -> right; 2 -> down; 3 -> left
    private static int[][] offset;

    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        openNum = 0;
        opened = new boolean[n * n + 2];
        for (int i = 0; i < n; i++) {
                opened[i] = false;
            }
        opened[0] = true;
        opened[opened.length - 1] = true;
        percolation = new WeightedQuickUnionUF(n * n + 2);
        full = new WeightedQuickUnionUF(n * n + 1);
        initOffset();
    }

    /**
     * Open the site (row, col) if it is not open already
     * @param row site's row number
     * @param col site's col number
     */
    public void open(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openNum++;
        opened[index] = true;
        unionAllNeighbors(row, col);

    }

    /**
     * Test is site (row, col) opened or not.
     * @param row site's row
     * @param col site's col
     * @return opened >> true; not opened >> false
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        return opened[index];
    }

    /**
     * Test is site (row, col) is full or not.
     * @param row site's row number
     * @param col site's col number
     * @return full >> true; not full >> false;
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        return full.find(0) == full.find(index);
    }

    /**
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openNum;
    }

    /**
     * Test Can the system percolate or not.
     * @return can >> true; can't >> false;
     */
    public boolean percolates() {
        return percolation.find(0) == percolation.find(n * n + 1);
    }

    /**
     * map site (row, col) to an index in the array
     * @param row site's row number
     * @param col site's col number
     * @return index in the array
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * n + col;
    }

    /**
     * initialize member variable offset
     */
    private void initOffset() {
        offset = new int[4][2];
        offset[0][0] = -1;
        offset[0][1] = 0;
        offset[1][0] = 0;
        offset[1][1] = 1;
        offset[2][0] = 1;
        offset[2][1] = 0;
        offset[3][0] = 0;
        offset[3][1] = -1;
    }

    /**
     * Test coordinate(row, col) is in the correct range or not.
     * @param row site's row number
     * @param col site's col number
     */
    private void validate(int row, int col) {
        if (row <= 0 || row > n) {
            String msg = "row: " + row +" is out of range [0, " + n + "].";
            throw new IllegalArgumentException(msg);
        }
        if (col <= 0 || col > n) {
            String msg = "col: " + col +" is out of range [0, " + n + "].";
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Test coordinate(row, col) is in the correct range or not.
     * Implement this method by invoking method validate()
     * and check is there an exception or not.
     * @param row site's row number
     * @param col site's col number
     * @return true if in there, otherwise false
     */
    private boolean withRange(int row, int col) {
        try {
            validate(row, col);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * @return the last index of the arr in uf
     */
    private int last() {
        return n * n + 1;
    }

    /**
     * @return the first index of the arr in uf
     */
    private int first() {
        return 0;
    }

    /**
     * union site (0, col) with first site(the dummy site on the first row)
     * @param col site's col number
     */
    private void unionFirst(int col) {
        full.union(first(), xyTo1D(1, col));
        percolation.union(first(), xyTo1D(1, col));
    }

    /**
     * union site(n, col) with last site(the dummy site under the last row)
     * @param col site's col number
     */
    private void unionLast(int col) {
        percolation.union(last(), xyTo1D(n, col));
    }

    /**
     * union site (row, col) with his neighbor specified by direction
     * @param row site's row
     * @param col site's col
     * @param direction 0 >> up
     *                  1 >> right
     *                  2 >> down
     *                  3 >> left
     */
    private void unionNeighbor(int row, int col, int direction) {
        int nRow = row + offset[direction][0];
        int nCol = col + offset[direction][1];
        if (withRange(nRow, nCol) && isOpen(nRow, nCol)) {
            percolation.union(xyTo1D(row, col), xyTo1D(nRow, nCol));
            full.union(xyTo1D(row, col), xyTo1D(nRow, nCol));
        }
    }

    /**
     * union site(row, col) with his neighbor in its up
     * @param row site's row
     * @param col site's col
     */
    private void unionUp(int row, int col) {
        if (row == 1) {
            unionFirst(col);
            return;
        }
        unionNeighbor(row, col, 0);
    }

    /**
     * union site (row, col) with neighbor in his right
     * @param row site's row number
     * @param col site's col number
     */
    private void unionRight(int row, int col) {
        unionNeighbor(row, col, 1);
    }


    /**
     * union site(row, col) with neighbor in his down
     * @param row site's row number
     * @param col site's col number
     */
    private void unionDown(int row, int col) {
        if (row == n) {
            unionLast(col);
            return;
        }
        unionNeighbor(row, col, 2);
    }

    /**
     * union site (row, col) with his neighbor in its left
     * @param row site's row
     * @param col site's col
     */
    private void unionLeft(int row, int col) {
        unionNeighbor(row, col, 3);
    }

    /**
     * union site (row, col) with all his neighbors
     * @param row site's row
     * @param col site's col
     */
    private void unionAllNeighbors(int row, int col) {
        unionUp(row, col);
        unionRight(row, col);
        unionDown(row, col);
        unionLeft(row, col);
    }
}
