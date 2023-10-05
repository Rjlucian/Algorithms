import static utils.SortUtils.*;

import java.util.Arrays;
import java.util.Comparator;
public class Insertion {

    private Insertion() {

    }

    /**
     * 使用自然序列以及<code>Comparable</code>接口
     * 下面写法与<code>public static &lt;Key&gt; void sort(Comparable<Key>[] a)</code>的不同之处:<br>
     * 1. 注释中的写法可以传递任何实现了<code>Comparable</code>接口的对象数组给这个函数, 数组中的对象不一定可比<br>
     * 2. 下列下法保证了数组中的元素都是可比的对象
     */
    public static <T extends Comparable<T>> void sort(T[] a) {
        int n = a.length;
        // i: 有序部分的后一位, 一位默认有序, 所以i从1开始
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static <T> void sort(T[] a, Comparator<T> comparator) {
        int n = a.length;
        // i: 有序部分的后一位, 一位默认有序, 所以i从1开始
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(comparator, a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = {5, 4, 3, 2, 1};
        sort(a);
        System.out.println(Arrays.toString(a));
    }


}
