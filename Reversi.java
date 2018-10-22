import java.util.Arrays;
import java.util.Scanner;

/**
 * CS18000 - Fall 2018
 *
 * Project 2 - Reversi
 *
 * Abstraction of and launcher for a Reversi game
 *
 * @author Imtiaz Karim, karim7@purdue.edu
 * @author Marina Haliem, mwadea@purdue.edu
 * @author Evan Wang, wang3407@purdue.edu
 *
 * @version 10/10/18
 */
public class Reversi {
	

    public static boolean isEmpty(Point[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i].x != -1 || p[i].y != -1) {
                return false;
            }
        }
        return true;
    }

    //Check whether a Point is the Points array or not 

    public static boolean contains(Point[] points, Point p) {
       	for (int i = 0; i < points.length; i++) {
       		if (points[i].x == p.x && points[i].y == p.y) {
       		    return true;
            }
		}
        return false;
    }

    public static void start(Game g) {
        Scanner scan = new Scanner(System.in);

        String draw = "It is a draw.";
        String whiteWins = "White wins: " + g.wScore + ":" + g.bScore;
        String blackWins = "Black wins: " + g.bScore + ":" + g.wScore;
        String exit = "Exiting!";

        String blackMove = "Place move (Black): row then column: ";
        String blackSkipping = "Black needs to skip... Passing to white";
        String invalidBlackMove = "Invalid move!\nPlace move (Black): row then column: ";
        String blackScoreShow = "Black: " + g.bScore + " White: " + g.wScore;

        String whiteSkipping = "White needs to skip... Passing to Black";
        String whiteMove = "Place move (White): row then column: ";
        String invalidWhiteMove = "Invalid move!\nPlace move (White): row then column: ";
        String whiteScoreShow = "White: " + g.wScore + " Black: " + g.bScore;
        boolean blackSkip;
        boolean whiteSkip;

        while (!isEmpty(g.getPlaceableLocations('B', 'W')) &&
                !isEmpty(g.getPlaceableLocations('W', 'B'))) {
            if (isEmpty(g.getPlaceableLocations('B', 'W'))) {
                System.out.println(blackSkipping);
                blackSkip = true;
            } else {
                blackSkip = false;
            }
            if (!blackSkip) {
                g.showPlaceableLocations(g.getPlaceableLocations('B', 'W'), g);
                System.out.println(blackMove);
                String resB = "";
                while (!scan.hasNext("exit") && !scan.hasNextInt()) {
                    System.out.println(invalidBlackMove);
                    resB = scan.nextLine();
                }
                if (resB.isEmpty()) {
                    resB = scan.nextLine();
                }
                if (resB.toLowerCase().equals("exit")) {
                    System.out.println(exit);
                    return;
                }
                int y = Integer.parseInt(resB.substring(0, 1)) - 1;
                int x = Integer.parseInt(resB.substring(1)) - 1;
                while (!contains(g.getPlaceableLocations('B', 'W'), new Point(x, y))) {
                    System.out.println(invalidBlackMove);
                    while (!scan.hasNext("exit") && !scan.hasNextInt()) {
                        System.out.println(invalidBlackMove);
                        resB = scan.nextLine();
                    }
                    resB = scan.nextLine();
                    if (resB.toLowerCase().equals("exit")) {
                        System.out.println(exit);
                        return;
                    }
                    y = Integer.parseInt(resB.substring(0, 1)) - 1;
                    x = Integer.parseInt((resB.substring(1))) - 1;
                }

                g.placeMove(new Point(x, y), 'B', 'W');

                g.updateScores();
                System.out.println("Black: " + g.bScore + " White: " + g.wScore);
                System.out.println();
            }
            if (isEmpty(g.getPlaceableLocations('W', 'B'))) {
                System.out.println(whiteSkipping);
                whiteSkip = true;
            } else {
                whiteSkip = false;
            }
            if (!whiteSkip) {
                g.showPlaceableLocations(g.getPlaceableLocations('W', 'B'), g);
                System.out.println(whiteMove);
                String resB = "";
                while (!scan.hasNext("exit") && !scan.hasNextInt()) {
                    System.out.println(invalidWhiteMove);
                    resB = scan.nextLine();
                }
                if (resB.isEmpty()) {
                    resB = scan.nextLine();
                }
                if (resB.equals("exit")) {
                    System.out.println(exit);
                    return;
                }
                int y;
                int x;
                y = Integer.parseInt(resB.substring(0, 1)) - 1;
                x = Integer.parseInt((resB.substring(1))) - 1;

                while (!contains(g.getPlaceableLocations('W', 'B'), new Point(x, y))) {
                    System.out.println(invalidWhiteMove);
                    while (!scan.hasNext("exit") && !scan.hasNextInt()) {
                        System.out.println(invalidWhiteMove);
                        resB = scan.nextLine();
                    }
                    resB = scan.nextLine();
                    if (resB.toLowerCase().equals("exit")) {
                        System.out.println(exit);
                        return;
                    }
                    y = Integer.parseInt(resB.substring(0, 1)) - 1;
                    x = Integer.parseInt((resB.substring(1))) - 1;
                }

                g.placeMove(new Point(x, y), 'W', 'B');

                g.updateScores();
                System.out.println("White: " + g.wScore + " Black: " + g.bScore);
            }
        }
        if (g.gameResult(g.getPlaceableLocations('W', 'B'),
                g.getPlaceableLocations('W', 'B')) == 0) {
            System.out.println(draw);
        }
        if (g.gameResult(g.getPlaceableLocations('W', 'B'),
                g.getPlaceableLocations('W', 'B')) == -1) {
            System.out.println("Black wins: " + g.bScore + ":" + g.wScore);
        }
        if (g.gameResult(g.getPlaceableLocations('W', 'B'),
                g.getPlaceableLocations('W', 'B')) == 1) {
            System.out.println("White wins: " + g.wScore + ":" + g.bScore);
        }
    }


    public static void main(String[] args) {
        Game b = new Game();
        start(b);
    }
}
