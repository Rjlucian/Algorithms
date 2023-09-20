import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_CAPACITY = 8;

    private Item[] data;
    private int capacity;
    private int last;

    public RandomizedQueue() {
        data = (Item[]) new Object[INIT_CAPACITY];
        capacity = INIT_CAPACITY;
        last = 0;
    }

    /**
     * @return Is the queue empty.
     */
    public boolean isEmpty() {
        return last == 0;
    }

    /**
     * @return the number of items on the deque.
     */
    public int size() {
        return last;
    }

    /**
     * Add the item.
     * @param item the item to be added.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to be added is null.");
        }
        if (last == capacity) {
            expand();
        }
        data[last] = item;
        last++;
    }


    /**
     * Remove and return a random item.
     * @return the item to be returned and removed.
     */
    public Item dequeue() {
        int index = getRandomIndex();
        Item item = data[index];
        for (int i = index; i < last - 1; i++) {
            data[i] = data[i + 1];
        }
        data[last - 1] = null;
        last--;
        if (capacity > 8 && (double) last / capacity < 0.25) {
            shrink();
        }
        return item;
    }


    /**
     * return a random item (but do not remove it)
     * @return the item to be returned.
     */
    public Item sample() {
        int index = getRandomIndex();
        return data[index];
    }

    /**
     * Double the size of array when it's full.
     */
    private void expand() {
        capacity = 2 * capacity;
        Item[] newData = (Item[]) new Object[capacity];
        if (last >= 0) {
            System.arraycopy(data, 0, newData, 0, last);
        }
        data = newData;

    }

    /**
     * half the size of array when it's usage < 0.25
     */
    private void shrink() {
        capacity = capacity / 2;
        Item[] newData = (Item[]) new Object[capacity];
        if (last >= 0) {
            System.arraycopy(data, 0, newData, 0, last);
        }
        data = newData;
    }

    /**
     * @return a random index in the array where {@code data[index] != null}.
     */
    private int getRandomIndex() {
        return StdRandom.uniformInt(last);
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int cur;
        private final Item[] IteratorData;

        public RandomizedQueueIterator() {
            cur = 0;
            IteratorData = (Item[]) new Object[last];
            System.arraycopy(data, 0, IteratorData, 0, last);
            StdRandom.shuffle(IteratorData);
        }

        @Override
        public boolean hasNext() {
            return cur != IteratorData.length;
        }

        @Override
        public Item next() {
            Item item = IteratorData[cur];
            cur++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        StdOut.println("Size: " + queue.size());
        for (var e : queue) {
            StdOut.print(e + " ") ;
        }
        StdOut.println();

        for (int i = 0; i < 5; i++) {
            StdOut.print(queue.sample() + " ");
        }
        StdOut.println();

        for (int i = 0 ; i < 10; i++) {
            StdOut.print(queue.dequeue() + " ");
        }

        StdOut.println();
        StdOut.println(queue.isEmpty());
    }
}
