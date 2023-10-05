package utils;

import java.util.Comparator;

/**
 * 为排序提供一系列辅助方法
 */
public class SortUtils {

    private SortUtils() {

    }

    public static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    public static <T> boolean less(Comparator<T> comparator, T v, T w) {
        return comparator.compare(v, w) < 0;
    }

    public static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static <T> boolean isSorted(T[] a, Comparator<T> comparator) {
        return isSorted(a, comparator, 0, a.length - 1);
    }

    public static <T> boolean isSorted(T[] a, Comparator<T> comparator, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(comparator, a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

}
