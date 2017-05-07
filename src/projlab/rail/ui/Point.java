package projlab.rail.ui;

public class Point {
    private final int x, y;

    /**
     * Sets the X and Y coordinates to the given values
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * True, if the other object is equals to the current
     * @param o Other object
     * @return Result of the comparison
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Point)) return false;
        Point p = (Point)o;
        return p.x == x && p.y == y;
    }

    /**
     *
     * @return Hash code of the object
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * Calculates the distance between 2 points
     * @param p The other point
     * @return Distance
     */
    public int getDist(Point p) {
        return Math.max(1, (int)Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y)));
    }

    /**
     * @return String representation of the point
     */
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
