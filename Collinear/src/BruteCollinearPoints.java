import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> segments;

    /**
     * Find all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        segments = new ArrayList<>();
        validatePointsNotNull(points);
        validateSinglePointNotNull(points);
        validatePointsNotEqual(points);

        // preventing changing content in the points.
        points = Arrays.copyOf(points, points.length);

        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[i].slopeTo(points[k]);
                        double s3 = points[i].slopeTo(points[l]);
                        if (s1 == s2 && s1 == s3) {
                            segments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    /**
     the number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * @return The line segments.
     */
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment e : segments) {
            ret[i++] = e;
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
                if (points[i].compareTo(points[j])== 0) {
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
