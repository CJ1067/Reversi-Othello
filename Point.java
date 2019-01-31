
/**
 * Abstraction for a point on a two-dimensional coordinate space
 *
 * @author Christopher Lehman, lehman40@purdue.edu
 *
 * @version 10/10/18
 *
 */
public class Point {

    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + "| " + y;
    }
}
