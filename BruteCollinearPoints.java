import java.util.Arrays;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private final Point[] point;
    private int n;

    public BruteCollinearPoints(Point[] points) // finds all line segments
                                                // containing 4 points

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

        ArrayList<Point> pr = new ArrayList<Point>();
        ArrayList<Double> slo = new ArrayList<Double>();
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        double s, s2, s3;
        for (int i = 0; i < point.length - 3; i++) {

            for (int j = i + 1; j < point.length - 2; j++) {
                s = point[i].slopeTo(point[j]);
                for (int k = j + 1; k < point.length - 1; k++) {
                    s2 = point[i].slopeTo(point[k]);
                    Point pt3 = null;
                    if (s == s2)
                        for (int b = k + 1; b < point.length; b++) {
                            s3 = point[i].slopeTo(point[b]);
                            if (s3 == s)
                                pt3 = point[b];

                        }
                    if (pt3 != null) {

                        ls.add(new LineSegment(point[i], pt3));
                        pr.add(pt3);
                        slo.add(s);

                    }
                }
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
