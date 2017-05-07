package projlab.rail.ui;

public class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Point)) return false;
        Point p = (Point)o;
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getDist(Point p) {
        System.out.print(this + " - " + p);
        return Math.max(1, (int)Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
