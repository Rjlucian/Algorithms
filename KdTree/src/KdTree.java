import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static double minDistance;
    private Node root;
    private int size;

    public KdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        root = insert(root, p, true);
    }

    private Node insert(Node node, Point2D p, boolean vertical) {
        if (node == null) {
            node = new Node();
            node.vertical = vertical;
            node.point = p;
            size++;
            return node;
        }

        int cmp;
        if (node.vertical) {
            cmp = Point2D.X_ORDER.compare(p, node.point);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, node.point);
        }

        vertical = !vertical;
        if (cmp < 0) {
            node.left = insert(node.left, p, vertical);
        } else if (cmp > 0){
            node.right = insert(node.right, p, vertical);
        } else {
            if (node.point.equals(p)) {
                return node;
            } else {
                node.right = insert(node.right, p, vertical);
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node temp = root;
        int cmp;
        while (temp != null) {

            if (temp.vertical) {
                cmp = Point2D.X_ORDER.compare(p, temp.point);
            } else {
                cmp = Point2D.Y_ORDER.compare(p, temp.point);
            }

            if (cmp < 0) {
                temp = temp.left;
            } else if (cmp > 0) {
                temp = temp.right;
            } else {
                if (temp.point.equals(p)) {
                    break;
                } else {
                    temp = temp.right;
                }
            }
        }
        return temp != null;
    }

    public void draw() {
        draw(root, 0, 0, 1, 1);
    }

    private void draw(Node node, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            return;
        }
        double radius = StdDraw.getPenRadius();
        StdDraw.setPenRadius(0.02);
        node.point.draw();
        StdDraw.setPenRadius(radius);
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), ymin, node.point.x(), ymax);
            StdDraw.setPenColor(StdDraw.BLACK);
            draw(node.left, xmin, ymin, node.point.x(), ymax);
            draw(node.right, node.point.x(), ymin, xmax, ymax);
        } else {
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(xmin, node.point.y(), xmax, node.point.y());
            StdDraw.setPenColor(StdDraw.BLACK);
            draw(node.left, xmin, ymin, xmax, node.point.y());
            draw(node.right, xmin, node.point.y(), xmax, ymax);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        List<Point2D> list = new ArrayList<>();
        RectHV curRect = new RectHV(0, 0, 1, 1);
        Node node = root;
        range(rect, curRect, list, node);
        return list;
    }

    private void range(RectHV rect, RectHV curRect, List<Point2D> list, Node node) {
        if (node == null) {
            return;
        }

        if (!rect.intersects(curRect)) {
            return;
        }

        if (rect.contains(node.point)) {
            list.add(node.point);
        }

        RectHV[] rectHVS = getTwoRect(curRect, node);
        RectHV leftRect = rectHVS[0];
        RectHV rightRect = rectHVS[1];
        range(rect, leftRect, list, node.left);
        range(rect, rightRect, list, node.right);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        minDistance = Double.MAX_VALUE;
        RectHV rect = new RectHV(0, 0, 1, 1);
        Node temp = root;
        return nearest(p, null, temp, rect);
    }

    private Point2D nearest(Point2D p, Point2D nearest, Node node, RectHV curRect) {
        if (node == null) {
            return nearest;
        }
        if (nearest == null) {
            nearest = node.point;
        }

        if (curRect.distanceSquaredTo(p) > minDistance) {
            return nearest;
        }
        double distance = node.point.distanceSquaredTo(p);
        if (distance < minDistance) {
            minDistance = distance;
            nearest = node.point;
        }


        RectHV[] rectHVS = getTwoRect(curRect, node);
        RectHV leftRect = rectHVS[0];
        RectHV rightRect = rectHVS[1];
        if (node.vertical) {
            if (Point2D.X_ORDER.compare(p, node.point) < 0) {
                nearest = nearest(p, nearest, node.left, leftRect);
                nearest = nearest(p, nearest, node.right, rightRect);
            } else {
                nearest = nearest(p, nearest, node.right, rightRect);
                nearest = nearest(p, nearest, node.left, leftRect);
            }
        } else {
            if (Point2D.Y_ORDER.compare(p, node.point) < 0) {
                nearest = nearest(p, nearest, node.left, leftRect);
                nearest = nearest(p, nearest, node.right, rightRect);
            } else {
                nearest = nearest(p, nearest, node.right, rightRect);
                nearest = nearest(p, nearest, node.left, leftRect);
            }
        }

        return nearest;
    }

    private RectHV[] getTwoRect(RectHV curRect, Node node) {
        RectHV leftRect;
        RectHV rightRect;
        if (node.vertical) {
            leftRect = new RectHV(curRect.xmin(), curRect.ymin(), node.point.x(), curRect.ymax());
            rightRect = new RectHV(node.point.x(), curRect.ymin(), curRect.xmax(), curRect.ymax());
        } else {
            leftRect = new RectHV(curRect.xmin(), curRect.ymin(), curRect.xmax(), node.point.y());
            rightRect = new RectHV(curRect.xmin(), node.point.y(), curRect.xmax(), curRect.ymax());
        }
        RectHV[] rectHVS = new RectHV[2];
        rectHVS[0] = leftRect;
        rectHVS[1] = rightRect;
        return rectHVS;
    }


    private static class Node {
        private Point2D point;      // the point
        private boolean vertical;
        private Node left;        // the left/bottom subtree
        private Node right;        // the right/top subtree
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        Point2D p = new Point2D(0.134, 0.134);
        System.out.println(brute.nearest(p));
        System.out.println(kdtree.nearest(p));

    }
}
