import java.util.Arrays;
import java.util.Comparator;

import static utils.SortUtils.*;

public class Selection {

    private Selection() {

    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int minIndex = findMinIndex(a, i);
            exchange(a, i, minIndex);
        }
    }

    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int minIndex = findMinIndex(a, comparator, i);
            exchange(a, i, minIndex);
        }
    }

    private static <T extends Comparable<T>> int findMinIndex(T[] a, int start) {
        int minIndex = start;
        for (int i = start + 1; i < a.length; i++) {
            if (less(a[i], a[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static <T> int findMinIndex(T[] a, Comparator<T> comparator, int start) {
        int minIndex = start;
        for (int i = start + 1; i < a.length; i++) {
            if (less(comparator, a[i], a[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void main(String[] args) {
        Integer[] a = {6, 4, 2, 12, 6, 123};
        sort(a);
        System.out.println(Arrays.toString(a));
    }


}
