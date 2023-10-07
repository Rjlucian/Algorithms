import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private boolean solvable;
    private List<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solvable = false;
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(0, initial, null));

        MinPQ<SearchNode> tMinPQ = new MinPQ<>();
        tMinPQ.insert(new SearchNode(0, initial.twin(), null));

        while (true) {
            SearchNode minNode = minPQ.delMin();
            Board min = minNode.board;
            if (min.isGoal()) {
                solution = new ArrayList<>();
                while (minNode != null) {
                    solution.add(minNode.board);
                    minNode = minNode.pre;
                }
                solvable = true;
                Collections.reverse(solution);
                break;
            } else {
                for (Board neighbor : min.neighbors()) {
                    if (minNode.pre == null) {
                        minPQ.insert(new SearchNode(minNode.moves + 1, neighbor, minNode));
                    } else if(!neighbor.equals(minNode.pre.board)) {
                        minPQ.insert(new SearchNode(minNode.moves + 1, neighbor, minNode));
                    }
                }
            }

            SearchNode tMinNode = tMinPQ.delMin();
            Board tMin = tMinNode.board;
            if (tMin.isGoal()) {
                break;
            } else {
                for (Board neighbor : tMin.neighbors()) {
                    if (tMinNode.pre == null) {
                        tMinPQ.insert(new SearchNode(tMinNode.moves + 1, neighbor, tMinNode));
                    } else if (!neighbor.equals(tMinNode.pre.board)) {
                        tMinPQ.insert(new SearchNode(tMinNode.moves + 1, neighbor, tMinNode));
                    }
                }
            }
        }


    }
    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solution == null) {
            return -1;
        } else {
            return solution.size() - 1;
        }
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final int moves;
        private final Board board;
        private final SearchNode pre;
        private final int priority;

        public SearchNode(int moves, Board board, SearchNode pre) {
            this.moves = moves;
            this.board = board;
            this.pre = pre;
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
