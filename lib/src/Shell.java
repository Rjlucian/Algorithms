import java.util.Comparator;

import static utils.SortUtils.*;
public class Shell {

    private Shell() {

    }

    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) {
            // 3x + 1序列: 1, 4, 13, 43,
             h = 3 * h + 1;
        }
        while (h >= 1) {
            // h-sort
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) {
            // 3x + 1序列: 1, 4, 13, 43,
            h = 3 * h + 1;
        }
        while (h >= 1) {
            // h-sort
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(comparator, a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

}
