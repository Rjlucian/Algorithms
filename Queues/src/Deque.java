import java.util.NoSuchElementException;

public class Deque<Item> {
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
    }

    private static class Node<Item> {
        private Item val;
        private Node<Item> pre;
        private Node<Item> next;
    }

}
