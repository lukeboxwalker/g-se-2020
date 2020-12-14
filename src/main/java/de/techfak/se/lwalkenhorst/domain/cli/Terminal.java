package de.techfak.se.lwalkenhorst.domain.cli;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Game;
import de.techfak.se.lwalkenhorst.domain.GameObserver;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Terminal implements GameObserver {

    private static final String EMPTY = " ";
    private static final String BREAK = "\n";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final Game game;
    private final AtomicBoolean running;
    private String[][] stringBoard;
    private int points;

    public Terminal(final Game game) {
        this.game = game;
        this.game.addListener(this);
        this.running = new AtomicBoolean(false);
        this.game.start();
    }

    public void listenForInstructions() {
        if (!running.get()) {
            running.set(true);
            startListener();
        }
    }

    private void startListener() {
        final Scanner scanner = new Scanner(System.in);
        while (running.get()) {
            final String cmd = scanner.nextLine();
            if (cmd != null) {
                if ("".equals(cmd)) {
                    this.kill();
                } else {
                    this.crossTiles(Arrays.asList(cmd.split(",")));
                }
            }
        }
    }

    public void kill() {
        running.set(false);
    }

    private void initBoard(final Board board) {
        final int lengthX = board.getLengthX();
        final int lengthY = board.getLengthY();
        this.stringBoard = new String[lengthX][lengthY];
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                if (board.getTileAt(x, y).isCrossed()) {
                    stringBoard[x][y] = String.valueOf(board.getTileAt(x, y).getColor().getIdentifier()).toUpperCase();
                } else {
                    stringBoard[x][y] = String.valueOf(board.getTileAt(x, y).getColor().getIdentifier());
                }
            }
        }
    }

    private void printBoard() {
        System.out.print(EMPTY + EMPTY);
        for (int x = 0; x < stringBoard.length; x++) {
            System.out.print(ALPHABET.charAt(x) + EMPTY);
        }
        System.out.print(BREAK);
        for (int y = 0; y < stringBoard[0].length; y++) {
            System.out.print(y + 1 + EMPTY);
            for (final String[] strings : stringBoard) {
                System.out.print(strings[y] + EMPTY);
            }
            System.out.print(BREAK);
        }
        System.out.print("\nEnter your Turn: ");
    }

    private void crossTiles(final List<String> coordinates) {
        final List<Position> positions = new ArrayList<>(coordinates.size());
        int posX;
        int posY;
        for (final String coordinate : coordinates) {
            posX = ALPHABET.indexOf(String.valueOf(coordinate.charAt(0)).toUpperCase(Locale.ROOT));
            if (coordinate.length() == 2 && Character.isDigit(coordinate.charAt(1)) && posX != -1) {
                posY = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
                positions.add(new Position(posX, posY));
            } else {
                System.out.print("Wrong coordinate format!");
                System.out.print("\nEnter your Turn: ");
                return;
            }
        }
        if (!game.crossTiles(positions)) {
            System.out.print("Could not cross these tile(s). Stick to the rules!\n");
            System.out.print("\nEnter your Turn: ");
        }
    }


    private void printPoints(final int points) {
        System.out.println("Game won points: " + points);
    }

    @Override
    public void onPointsChange(final int points, final List<Integer> fullColumns) {
        this.points = points;
    }

    @Override
    public void onGameStart(final Board board) {
        this.initBoard(board);
        System.out.println("Welcome to Encore!");
        this.printBoard();
    }


    @Override
    public void onTilesCross(final List<Position> positions) {
        for (final Position position : positions) {
            this.stringBoard[position.getPosX()][position.getPosY()] = stringBoard[position.getPosX()][position.getPosY()].toUpperCase();
        }
        this.printBoard();
    }

    @Override
    public void onGameEnd(final int points) {
        this.printPoints(points);
        this.kill();
    }
}
