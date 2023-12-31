import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String word = null;
        int count = 0;
        while (!StdIn.isEmpty()) {
            count++;
            String temp = StdIn.readString();
            if (StdRandom.bernoulli(1 / (double) count)) {
                word = temp;
            }
        }
        StdOut.println(word);
    }
}
