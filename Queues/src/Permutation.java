import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String x = StdIn.readString();
            queue.enqueue(x);
        }

        Iterator<String> iterator = queue.iterator();
        for (int i = 1; i <= k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
