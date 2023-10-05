import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        segments = new ArrayList<>();
        validatePointsNotNull(points);
        validateSinglePointNotNull(points);
        validatePointsNotEqual(points);

        int len = points.length;


        // This prohibits changing the content in the points.
        points = Arrays.copyOf(points, len);

        Point[] temp = Arrays.copyOf(points, len);

        for (Point point : points) {
            Arrays.sort(temp, point.slopeOrder());
            findSequence(temp, point);
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment e : segments) {
            ret[i++] = e;
        }
        return ret;
    }

    private void findSequence(Point[] points, Point curPoint) {
        int len = points.length;
        for (int left = 1; left < len;) {
            int right = left + 1;
            while (right < len && curPoint.slopeTo(points[left]) == curPoint.slopeTo(points[right])) {
                right++;
            }
            if (right - left >= 3 && curPoint.compareTo(min(points, left, right - 1)) < 0) {
                // (right - left) >= 3: When exit from the loop, points[index] is not
                // a slope to curPoint differing with previous points. So right - 1 + left + 1) + 1 >= 4
                // equals to (right - left) >= 3, the 1 outside the scoop brackets means curPoint itself.

                // Second condition makes sure that a new LineSegment will be added if and only if
                // the curPoint is located at the left down of the start point of the LineSegment.
                // It also means every lineSegments will only be added once.

                // From the Second condition, We know curPoint is the start point.
                // We need to find the end point of the LineSegment.
                // This avoids we add any subsegments.
                segments.add(new LineSegment(curPoint, max(points, left, right - 1)));
            }
            if (right == len) {
                break;
            }
            left = right;
        }
    }

    private Point min(Point[] a, int low, int high) {
        if (low > high || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[low];
        for (int i = low + 1; i <= high; i++) {
            if (ret.compareTo(a[i]) > 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    private Point max(Point[] a, int low, int high) {
        if (low > high || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[low];
        for (int i = low + 1; i <= high; i++) {
            if (ret.compareTo(a[i]) < 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    /**
     * Test if an array of point is null.
     */
    private void validatePointsNotNull(Point[] points) {
        if (points == null) {
            String msg = "points is null";
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Test if there is a null object in an array of Point.
     */
    private void validateSinglePointNotNull(Point[] points) {
        int i = 0;
        for (Point point : points) {
            if (point == null) {
                String msg = "point[" + i + "] is null";
                throw new IllegalArgumentException(msg);
            }
        }
    }

    /**
     * Test if there are duplicate points in an array of point.
     */
    private void validatePointsNotEqual(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    String msg = "point[" + i + "] and point[" + j + "] is equal";
                    throw new IllegalArgumentException(msg);
                }
            }
        }
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

