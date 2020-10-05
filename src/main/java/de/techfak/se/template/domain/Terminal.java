package de.techfak.se.template.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Terminal implements PropertyChangeListener {
    private static final String EXIT = "exit";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final BufferedReader bufferedReader;
    private final Game game;
    private final AtomicBoolean running;
    private String[][] stringBoard;

    public Terminal(final Game game) {
        this.game = game;
        this.game.addPropertyChangeListener(this);
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
        new Thread(() -> {
            while (running.get()) {
                try {
                    final String cmd = requestInput();
                    if (cmd != null) {
                        switch (cmd) {
                            case "":
                                this.game.rollDice();
                                break;
                            case EXIT:
                                this.kill();
                                break;
                            default:
                                try {
                                    crossTiles(Arrays.asList(cmd.split(",")));
                                } catch (UnknownCommandException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                        }
                    }
                } catch (IOException e) {
                    this.kill();
                }
            }
        }).start();
    }

    private String requestInput() throws IOException {
        if (!running.get()) {
            return null;
        }
        return bufferedReader.readLine();
    }

    public void kill() {
        running.set(false);
    }

    private void initBoard(Board board) {
        Tile[][] tiles = board.getTiles();
        if (tiles.length == 0) {
            return;
        }
        int lengthX = tiles.length;
        int lengthY = tiles[0].length;
        this.stringBoard = new String[lengthX][lengthY];
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                stringBoard[x][y] = String.valueOf(tiles[x][y].getColor().getIdentifier());
            }
        }
    }

    private void printBoard() {
        System.out.print("  | ");
        for (int x = 0; x < stringBoard.length; x++) {
            System.out.print(ALPHABET.charAt(x) + " ");
        }
        System.out.println("\n--|------------------------------");
        for (int y = 0; y < stringBoard[0].length; y++) {
            System.out.print(y + 1 + " | ");
            for (String[] strings : stringBoard) {
                System.out.print(strings[y] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private void crossTiles(List<String> coordinates) throws UnknownCommandException {
        final List<Position> positions = new ArrayList<>(coordinates.size());
        int x;
        int y;
        for (String coordinate : coordinates) {
            if (coordinate.length() == 2 && Character.isDigit(coordinate.charAt(1))
                    && (x = ALPHABET.indexOf(String.valueOf(coordinate.charAt(0)).toUpperCase())) != -1) {
                y = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
                positions.add(new Position(x, y));
            } else {
                throw new UnknownCommandException("Wrong coordinate format!");
            }
        }
        if (!game.crossTiles(positions)) {
            System.out.println("Could not cross these tile(s). Stick to the rules!");
        }
    }

    private void printDiceResult(DiceResult diceResult) {
        List<Color> colors = diceResult.getRolledColors();
        List<Integer> numbers = diceResult.getRolledNumbers();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nColors: ");
        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            stringBuilder.append(color.name());
            if (i + 1 < colors.size()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("\nNumbers: ");
        for (int i = 0; i < numbers.size(); i++) {
            int number = numbers.get(i);
            stringBuilder.append(number);
            if (i + 1 < numbers.size()) {
                stringBuilder.append(", ");
            }
        }
        System.out.println(stringBuilder.toString() + "\n");
    }

    private void printPoints(int points) {
        System.out.println("Points: " + points);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
            case "START":
                this.initBoard((Board) event.getNewValue());
                break;
            case "DICE_RESULT":
                this.printDiceResult((DiceResult) event.getNewValue());
                this.printBoard();
                break;
            case "CROSS_POSITIONS":
                final List<Position> positions = (List<Position>) event.getNewValue();
                for (Position position : positions) {
                    this.stringBoard[position.getX()][position.getY()] = "X";
                }
                break;
            case "END":
                final int points = (int) event.getNewValue();
                printPoints(points);
                this.kill();
                break;
            default:
                break;
        }
    }
}