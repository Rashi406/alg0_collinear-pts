import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final Point[] point;
    private int n;

    public FastCollinearPoints(Point[] points) // finds all line segments
                                               // containing 4 or more points
    {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
        }
        point = Arrays.copyOf(points, points.length);
        Arrays.sort(point);
        n = 0;
    }

    public int numberOfSegments() // the number of line segments
    {
        return n;
    }

    public LineSegment[] segments() // the line segments
    {
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        ArrayList<Point> pr = new ArrayList<Point>();
        ArrayList<Double> slo = new ArrayList<Double>();
        double slop = 0.0;
        int s = 1;
        Point pt3 = null;
        // Point[] p = Arrays.copyOf(point,point.length);
        for (int i = 0; i < point.length; i++) {
            Comparator<Point> pt = point[i].slopeOrder();
            Point[] p = Arrays.copyOfRange(point, i, point.length);
            Arrays.sort(p, pt);
            s = 1;
            pt3 = null;

            for (int j = 0; j < p.length - 1; j++) {
                if (pt.compare(p[j], p[j + 1]) == 0) {
                    s++;
                    if (s >= 3) {
                        if (pt3 == null || pt3.compareTo(p[j + 1]) < 0) {
                            pt3 = p[j + 1];
                            slop = point[i].slopeTo(p[j]);
                        }

                    }
                } else {
                    if (s >= 3) {
                        ls.add(new LineSegment(point[i], pt3));
                        pr.add(pt3);
                        slo.add(slop);
                    }
                    s = 1;
                    pt3 = null;
                }
            }

            if (s >= 3) {
                ls.add(new LineSegment(point[i], pt3));
                pr.add(pt3);
                slo.add(slop);

            }

        }

        pr.trimToSize();
        slo.trimToSize();
        ls.trimToSize();

        for (int i = 0; i < pr.size(); i++) {
            for (int j = i + 1; j < pr.size(); j++)
                if (pr.get(i).compareTo(pr.get(j)) == 0 && Double.compare(slo.get(i), slo.get(j)) == 0)
                    ls.set(j, null);
        }
        ArrayList<LineSegment> lb = new ArrayList<LineSegment>();

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) != null)
                lb.add(ls.get(i));
        }

        lb.trimToSize();

        LineSegment[] arr = new LineSegment[lb.size()];
        lb.toArray(arr);
        n = arr.length;
        return arr;

    }
}
