
/**
 * CS18000 - Fall 2018
 *
 * Project 2 - Reversi
 *
 * Abstraction for a point on a two-dimensional coordinate space
 *
 * @author Imtiaz Karim, karim7@purdue.edu
 * @author Marina Haliem, mwadea@purdue.edu
 * @author Evan Wang, wang3407@purdue.edu
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
