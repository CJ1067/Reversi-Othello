import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * Implementation of the game mechanics in Reversi
 *
 * @author Christopher Lehman
 *
 * @version 10/10/18
 */

public class Game {

    private final char[][] board;
    public int wScore;
    public int bScore;
    public int remaining;
    private final char [] boardX = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};

    public Game() {
        board = new char[][]{
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', 'W', 'B', '_', '_', '_'},
            {'_', '_', '_', 'B', 'W', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'}};
    }

    public void displayBoard(Game b) {
        System.out.print("  ");
        for (int i = 0; i < b.getBoardX().length; i++) {
            System.out.print(boardX[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < b.getBoardX().length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < b.getBoardX().length; j++) {
                System.out.print(b.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //There are three cases black win = -1, white win = 1, draw = 0 

    public int gameResult(Point[] whitePlaceableLocations, Point[] blackPlaceableLocations) {

        if (wScore == bScore)
            return 0;
        if (wScore > bScore) {
            return 1;
        }
        return -1;
    }

    public Point[] getPlaceableLocations(char player, char opponent) {
        Point[] placeablePositions = new Point[64];
        boolean continueOn = true;
        boolean opponentFound = false;
        int ri = 0;
        int rj = 0;
        int copyI;
        int copyJ;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player) {
                    for (int direction = 0; direction < 8; direction++) {
                        switch (direction) {
                            case 0: //to the left
                                ri = 0;
                                rj = -1;
                                break;
                            case 1: // to the right
                                ri = 0;
                                rj = 1;
                                break;
                            case 2: //up
                                ri = -1;
                                rj = 0;
                                break;
                            case 3: //down
                                ri = 1;
                                rj = 0;
                                break;
                            case 4: //up left
                                ri = -1;
                                rj = -1;
                                break;
                            case 5: //up right
                                ri = -1;
                                rj = 1;
                                break;
                            case 6: //down right
                                ri = 1;
                                rj = 1;
                                break;
                            case 7: //down left
                                ri = 1;
                                rj = -1;
                                break;
                        }
                        copyI = i + ri;
                        copyJ = j + rj;
                        while (continueOn && isInBounds(new Point(copyJ, copyI))) {
                            if (board[copyI][copyJ] == '_' && !opponentFound) {
                                continueOn = false;
                            } else if (board[copyI][copyJ] == opponent) {
                                opponentFound = true;
                            } else if (board[copyI][copyJ] == player) {
                                continueOn = false;
                            } else if (board[copyI][copyJ] == '_') {
                                placeablePositions[copyI * 8 + copyJ] = new Point(copyJ, copyI);
                                continueOn = false;
                            }
                            copyI += ri;
                            copyJ += rj;
                        }
                        continueOn = true;
                        opponentFound = false;
                    }
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (placeablePositions[i * 8 + j] == null)
                    placeablePositions[i * 8 + j] = new Point(-1, -1);
            }
        }
        return placeablePositions;
    }

    public void showPlaceableLocations(Point[] locations, Game b) {
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].x != -1)
                board[locations[i].y][locations[i].x] = '*';
        }
        displayBoard(b);
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].x != -1)
                board[locations[i].y][locations[i].x] = '_';
        }
    }

    public void placeMove(Point p, char player, char opponent) {
        boolean continueOn = true;
        boolean opponentFound = false;
        int ri = 0;
        int rj = 0;
        int copyI;
        int copyJ;
        int counter = 1;

        for (int direction = 0; direction < 8; direction++) {
            switch (direction) {
                case 0: //to the left
                    ri = 0;
                    rj = -1;
                    break;
                case 1: // to the right
                    ri = 0;
                    rj = 1;
                    break;
                case 2: //up
                    ri = -1;
                    rj = 0;
                    break;
                case 3: //down
                    ri = 1;
                    rj = 0;
                    break;
                case 4: //up left
                    ri = -1;
                    rj = -1;
                    break;
                case 5: //up right
                    ri = -1;
                    rj = 1;
                    break;
                case 6: //down right
                    ri = 1;
                    rj = 1;
                    break;
                case 7: //down left
                    ri = 1;
                    rj = -1;
                    break;
            }
            copyI = p.y + ri;
            copyJ = p.x + rj;

            while (continueOn && isInBounds(new Point(copyJ, copyI))) {
                if (board[copyI][copyJ] == '_') {
                    continueOn = false;
                } else if (board[copyI][copyJ] == opponent) {
                    opponentFound = true;
                    counter++;
                } else if (board[copyI][copyJ] == player && !opponentFound) {
                    continueOn = false;
                } else if (board[copyI][copyJ] == player) {
                    for (int i = 0; i < counter; i++) {
                        copyI -= ri;
                        copyJ -= rj;
                        board[copyI][copyJ] = player;
                    }
                    counter = 1;
                    continueOn = false;
                }
                copyI += ri;
                copyJ += rj;
            }
            counter = 1;
            continueOn = true;
            opponentFound = false;
        }
    }


    public void updateScores() {
        wScore = 0;
        bScore = 0;
        remaining = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'W')
                    wScore++;
                if (board[i][j] == 'B')
                    bScore++;
                if (board[i][j] == '_')
                    remaining++;
            }
        }
    }

    public static boolean isInBounds(Point p) {
        return p.x > -1 && p.x < 8 && p.y > -1 && p.y < 8;
    }

    public char[][] getBoard() {
        return board;
    }

    public char[] getBoardX() {
        return boardX;
    }
}
