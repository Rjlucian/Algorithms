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
        for (int i = 1; i < len;) {
            int j = i + 1;
            while (j < len && curPoint.slopeTo(points[i]) == curPoint.slopeTo(points[j])) {
                j++;
            }
            if (j - i >= 3 && curPoint.compareTo(min(points, i, j - 1)) < 0) {
                segments.add(new LineSegment(curPoint, max(points, i, j - 1)));
            }
            if (j == len) {
                break;
            }
            i = j;
        }
    }

    private Point min(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
            if (ret.compareTo(a[i]) > 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    private Point max(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
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

