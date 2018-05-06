import java.util.*;
import java.io.*;
import java.math.*;
import java.awt.geom.*;
import java.awt.geom.*;
public class Solution {
  public static final BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
  public static final PrintWriter outWriter = new PrintWriter(System.out);
  public static final double TH2_MAX = 0.6154797;
  public static class Point3D {
    public double x;
    public double y;
    public double z;
    public double x() { return x; }
    public double y() { return y; }
    public double z() { return z; }
    Point3D(double x_, double y_, double z_) { x=x_; y=y_; z=z_; }
    public String toString() { return String.format("%.15f %.15f %.15f", x, y, z); }
    public void tformXY(AffineTransform t) {
      Point2D.Double p = new Point2D.Double(x,y);
      t.transform(p,p);
      x=p.x;
      y=p.y;
    }
    public void tformXZ(AffineTransform t) {
      Point2D.Double p = new Point2D.Double(x,z);
      t.transform(p,p);
      x=p.x;
      z=p.y;
    }
    public void tformYZ(AffineTransform t) {
      Point2D.Double p = new Point2D.Double(y,z);
      t.transform(p,p);
      y=p.x;
      z=p.y;
    }
    public Point3D copy() { return new Point3D(x,y,z); }
    public Point3D inv() { return new Point3D(-x,-y,-z); }
    public double len() { return Math.sqrt(x*x + y*y + z*z); }
  }
  public static List<Point2D.Double> convexHull(Point2D.Double[] points) {
    ArrayList<Point2D.Double> out = new ArrayList<Point2D.Double>();
    Point2D.Double p0 = Collections.min(Arrays.asList(points), Comparator.comparing(Point2D.Double::getX).thenComparing(Point2D.Double::getY));
    while (out.size() <= 1 || out.get(0) != p0) {
      out.add(p0);
      Point2D.Double p1 = points[0];
      for (int i = 1; i < points.length; i++) {
        if (p0.equals(p1) || isRight(p0, p1, points[i])) {
          p1 = points[i];
        }
      }
      p0 = p1;
    }
    return out;
  }
  public static boolean isRight(Point2D.Double a, Point2D.Double b, Point2D.Double c){
    return ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x)) < 0;
  }
  public static double area(List<Point2D.Double> points) {
    double out = 0;
    for (int i = 0; i < points.size(); i++) {
      Point2D.Double a = points.get(i), b = points.get((i+1)%points.size());
      out += a.x*b.y - a.y*b.x;
    }
    return out/2;
  }
  public static double shadowArea(Point3D[] corners) {
    Point2D.Double[] proj = new Point2D.Double[corners.length];
    for (int i = 0; i < proj.length; i++) { proj[i] = new Point2D.Double(corners[i].x, corners[i].z); }
    return area(convexHull(proj));
  }
  public static double areaAfterRotation(Point3D[] corners_, double th) {
    Point3D[] corners = new Point3D[corners_.length];
    for (int i = 0; i < corners.length; i++) { corners[i] = corners_[i].copy(); }
    AffineTransform tform = AffineTransform.getRotateInstance(th);
    for (Point3D p : corners) { p.tformYZ(tform); }
    return shadowArea(corners);
  }
  public static double th2(AffineTransform tform1, double d) {
    Point3D[] corners = new Point3D[] {
      new Point3D( 0.5, 0.5, 0.5),
      new Point3D(-0.5, 0.5, 0.5),
      new Point3D( 0.5, -0.5, 0.5),
      new Point3D(-0.5, -0.5, 0.5),
      new Point3D( 0.5, 0.5, -0.5),
      new Point3D(-0.5, 0.5, -0.5),
      new Point3D( 0.5, -0.5, -0.5),
      new Point3D(-0.5, -0.5, -0.5)
    };
    for (Point3D p : corners) { p.tformXY(tform1); }
    double lo = 0, hi = TH2_MAX;
    for (int i = 0; i < 100; i++) {
      double mid = (lo+hi)/2;
      if (areaAfterRotation(corners, mid) > d) {
        hi = mid;
      } else {
        lo = mid;
      }
    }
    return (hi+lo)/2;
  }
  public static double angleBetween(Point3D a, Point3D b) {
    Point3D v1 = a.inv(), v2 = b;
    double dot = v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    return Math.acos(dot/(v1.len()*v2.len()));
  }
  public static void check(double a, double b, String msg) {
    if (Math.abs(a-b) > 1e-6) {
      throw new RuntimeException(String.format("%s: exected %f, got %f",msg,b,a));
    }
  }
  public static Point3D intersect(Point3D a, Point3D b, Point3D c) {
    return new Point3D(a.x+b.x+c.x, a.y+b.y+c.y, a.z+b.z+c.z);
  }
  public static <F> F pty(int i, int ndx, F[] a, F[] b) {
    return ((((1<<ndx)&i) == 0) ? a : b)[ndx];
  }
  public static Point3D[] cornersFromFaces(Point3D[] f1) {
    Point3D[] f2 = new Point3D[f1.length];
    for (int i = 0; i < f2.length; i++) { f2[i] = f1[i].inv(); }
    Point3D[] corners = new Point3D[8];
    for (int i = 0; i < corners.length; i++) { corners[i] = intersect(pty(i,0,f1,f2),pty(i,1,f1,f2),pty(i,2,f1,f2)); }
    return corners;
  }
  public static void check(Point3D[] faces, double d) {
    for (Point3D p : faces) {
      check(p.len(), 0.5, "dist");
    }
    for (int i = 0; i < faces.length; i++) {
      for (int j = i+1; j < faces.length; j++) {
        check(angleBetween(faces[i], faces[j]), Math.PI/2, "angle");
      }
    }
    Point3D[] corners = cornersFromFaces(faces);
    check(shadowArea(corners), d, "area");
  }
  public static Object solve() {
    double d = nextDouble();
    double th;
    boolean flag;
    if (d <= Math.sqrt(2)) {
      th = Math.asin(d/Math.sqrt(2)) + Math.PI/4;
      flag = false;
    } else {
      th = 3*Math.PI/4;
      flag = true;
    }
    AffineTransform tform = AffineTransform.getRotateInstance(th);
    Point3D[] centers = new Point3D[] {new Point3D(0.5, 0, 0), new Point3D(0, 0.5, 0), new Point3D(0, 0, 0.5)};
    for (Point3D p : centers) {
      p.tformXY(tform);
    }
    if (flag) {
      AffineTransform tform2 = AffineTransform.getRotateInstance(th2(tform,d));
      for (Point3D p : centers) {
        p.tformYZ(tform2);
      }
    }
    outWriter.println();
    for (Point3D p : centers) {
      outWriter.println(p);
    }
    outWriter.flush();
    check(centers, d);
    return null;
  }
  public static void main(String[] args) {
    int T = nextInt();
    for (int i = 0; i < T; i++) {
      outWriter.print("Case #"+(i+1)+": ");
      Object tmp = solve();
      if (tmp != null) { outWriter.println(tmp); }
    }
    outWriter.flush();
  }
  public static StringTokenizer tokenizer = null;
  public static String nextLine() {
    try { return buffer.readLine(); } catch (IOException e) { throw new UncheckedIOException(e); }
  }
  public static String next() {
    while (tokenizer == null || !tokenizer.hasMoreElements()) { tokenizer = new StringTokenizer(nextLine()); }
    return tokenizer.nextToken();
  }
  public static int nextInt() { return Integer.parseInt(next()); }
  public static long nextLong() { return Long.parseLong(next()); }
  public static double nextDouble() { return Double.parseDouble(next()); }
}
