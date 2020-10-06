package de.techfak.se.template.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class Terminal implements PropertyChangeListener {

    private static final String EMPTY = " ";
    private static final String SEPARATE = ", ";
    private static final String BREAK = "\n";
    private static final String CROSS = "X";
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

    private void initBoard(final Board board) {
        final int lengthX = board.getLengthX();
        final int lengthY = board.getLengthY();
        this.stringBoard = new String[lengthX][lengthY];
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                if (board.getTileAt(x, y).isCrossed()) {
                    stringBoard[x][y] = CROSS;
                } else {
                    stringBoard[x][y] = String.valueOf(board.getTileAt(x, y).getColor().getIdentifier());
                }
            }
        }
    }

    private void printBoard() {
        System.out.print("  | ");
        for (int x = 0; x < stringBoard.length; x++) {
            System.out.print(ALPHABET.charAt(x) + EMPTY);
        }
        System.out.println("\n--|------------------------------");
        for (int y = 0; y < stringBoard[0].length; y++) {
            System.out.print(y + 1 + " | ");
            for (final String[] strings : stringBoard) {
                System.out.print(strings[y] + EMPTY);
            }
            System.out.print(BREAK);
        }
        System.out.print(BREAK);
    }

    private void crossTiles(final List<String> coordinates) throws UnknownCommandException {
        final List<Position> positions = new ArrayList<>(coordinates.size());
        int posX;
        int posY;
        for (final String coordinate : coordinates) {
            posX = ALPHABET.indexOf(String.valueOf(coordinate.charAt(0)).toUpperCase(Locale.ROOT));
            if (coordinate.length() == 2 && Character.isDigit(coordinate.charAt(1)) && posX != -1) {
                posY = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
                positions.add(new Position(posX, posY));
            } else {
                throw new UnknownCommandException("Wrong coordinate format!");
            }
        }
        if (!game.crossTiles(positions)) {
            System.out.println("Could not cross these tile(s). Stick to the rules!\n");
        }
    }

    private void printDiceResult(final DiceResult diceResult) {
        final List<Color> colors = diceResult.getRolledColors();
        final List<Integer> numbers = diceResult.getRolledNumbers();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nColors: ");
        for (int i = 0; i < colors.size(); i++) {
            final Color color = colors.get(i);
            stringBuilder.append(color.name());
            if (i + 1 < colors.size()) {
                stringBuilder.append(SEPARATE);
            }
        }
        stringBuilder.append("\nNumbers: ");
        for (int i = 0; i < numbers.size(); i++) {
            final int number = numbers.get(i);
            stringBuilder.append(number);
            if (i + 1 < numbers.size()) {
                stringBuilder.append(SEPARATE);
            }
        }
        System.out.println(stringBuilder.toString() + BREAK);
    }

    private void printPoints(final int points) {
        System.out.println("Game won points: " + points);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(final PropertyChangeEvent event) {
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
                for (final Position position : positions) {
                    this.stringBoard[position.getPosX()][position.getPosY()] = CROSS;
                }
                break;
            case "END":
                this.printPoints((int) event.getNewValue());
                this.kill();
                break;
            default:
                break;
        }
    }
}
