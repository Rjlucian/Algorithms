import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private static final int[][] offset = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private final int[] tiles;
    private final int dimension;
    private int zeroPosition;
    private int hamming;
    private int manhattan;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = new int[dimension * dimension];
        int cur = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[cur] = tiles[i][j];
                if (this.tiles[cur] == 0) {
                    zeroPosition = cur;
                }
                cur++;
            }
        }
        setHamming();
        setManhattan();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dimension);
        for (int i = 0; i < dimension; i++) {
            str.append('\n');
            for (int j = 0; j < dimension; j++) {
                str.append(' ').append(tiles[xyTo1D(i, j)]);
            }
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        if (y == this) {
            return true;
        }
        Board that = (Board) y;
        if (this.dimension != that.dimension) {
            return false;
        }
        for (int i = 0; i < dimension * dimension; i++) {
            if (this.tiles[i] != that.tiles[i]) {
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < offset.length; i++) {
            addNeighborByDirection(neighbors, i);
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i;
        int j;
        int index = 0;
        while (true) {
            if (tiles[index] != 0) {
                i = index;
                break;
            }
            index++;
        }
        index++;
        while (true) {
            if (tiles[index] != 0) {
                j = index;
                break;
            }
            index++;
        }
      return new Board(tiles, i, j, 0);
    }

    private void addNeighborByDirection(List<Board> neighbors, int direction) {
        int zeroX = _1DToX(zeroPosition);
        int zeroY = _1DToY(zeroPosition);
        int newZeroX = zeroX + offset[direction][0];
        int newZeroY = zeroY + offset[direction][1];
        if (inRange(newZeroX, newZeroY)) {
            int newZeroPosition = xyTo1D(newZeroX, newZeroY);
            Board neighbor = new Board(tiles, zeroPosition, newZeroPosition);
            neighbors.add(neighbor);
        }
    }

    private int xyTo1D(int x, int y) {
        return x * dimension + y;
    }

    private int _1DToX(int index) {
        return index / dimension;
    }

    private int _1DToY(int index) {
        return index % dimension;
    }

    private void setHamming() {
        hamming = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            if (tiles[i] == 0) {
                continue;
            }
            if ((tiles[i] - 1) != i) {
                hamming++;
            }
        }
    }

    private void setManhattan() {
        manhattan = 0;
        for (int i = 0; i < dimension * dimension; i++) {
            if (tiles[i] == 0) {
                continue;
            }
            int ax = _1DToX(i);
            int ay = _1DToY(i);
            int bx = _1DToX(tiles[i] - 1);
            int by = _1DToY(tiles[i] - 1);
            manhattan += (Math.abs(ax - bx) + Math.abs(ay - by));
        }
    }

    private boolean inRange(int x, int y) {
        return x >= 0 && x < dimension && y >= 0 && y < dimension;
    }

    private void exchange(int i, int j) {
        int swap = tiles[i];
        tiles[i] = tiles[j];
        tiles[j] = swap;
    }

    private Board(int[] tiles, int oldZeroPosition, int newZeroPosition) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        dimension = (int) Math.sqrt(tiles.length);
        exchange(oldZeroPosition, newZeroPosition);
        zeroPosition = newZeroPosition;
        setManhattan();
        setHamming();
    }

    private Board(int[] tiles, int i, int j, int signBit) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        dimension = (int) Math.sqrt(tiles.length);
        exchange(i, j);
        setManhattan();
        setHamming();
    }


    public static void main(String[] args) {
        int[][] a = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b = new Board(a);
        System.out.println(b);
        System.out.println(b.twin());
    }
}
