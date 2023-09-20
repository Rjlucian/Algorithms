import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final Node<Item> sentinel;
    private int size;

    public Deque() {
        sentinel = new Node<>();
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * @return Whether the deque is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of elements in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Add an item to the front.
     * @param item The item to be added.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null.");
        }
        size++;
        Node<Item> newFirst = new Node<>();
        Node<Item> oldFirst = sentinel.next;
        newFirst.val = item;
        newFirst.pre = sentinel;
        newFirst.next = oldFirst;
        sentinel.next = newFirst;
        oldFirst.pre = newFirst;
    }

    /**
     * Add an item to the back.
     * @param item The item to be added.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can't be null.");
        }
        size++;
        Node<Item> newTail = new Node<>();
        Node<Item> oldTail = sentinel.pre;
        newTail.val = item;
        newTail.pre = oldTail;
        newTail.next = sentinel;
        sentinel.pre = newTail;
        oldTail.next = newTail;
    }

    /**
     * remove and return the item from the front.
     * @return The first item in the deque.
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty.");
        }
        size--;
        Node<Item> oldFirst = sentinel.next;
        Node<Item> newFirst = oldFirst.next;
        oldFirst.pre = null;
        oldFirst.next = null;
        sentinel.next = newFirst;
        newFirst.pre = sentinel;
        return oldFirst.val;
    }

    /**
     * remove and return the item from the front.
     * @return The last item in the deque.
     */
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty.");
        }
        size--;
        Node<Item> oldLast = sentinel.pre;
        Node<Item> newLast = oldLast.pre;
        oldLast.pre = null;
        oldLast.next = null;
        sentinel.pre = newLast;
        newLast.next = sentinel;
        return oldLast.val;
    }

    private static class Node<Item> {
        private Item val;
        private Node<Item> pre;
        private Node<Item> next;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> cur = sentinel.next;

        @Override
        public boolean hasNext() {
            return cur != sentinel;
        }

        @Override
        public Item next() {
            Item val = cur.val;
            cur = cur.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 1; i <= 5; i++) {
            deque.addLast(i);
            deque.addFirst(11 - i);
        }

        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();

        StdOut.println("Size: " + deque.size());
        StdOut.print("All the elements: ");
        for (var e : deque) {
            StdOut.print(e + " ");
        }
        StdOut.println();

        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();

        StdOut.println("Size: " + deque.size());
        StdOut.print("All the elements: ");
        for (var e : deque) {
            StdOut.print(e + " ");
        }
        StdOut.println();

    }

}
