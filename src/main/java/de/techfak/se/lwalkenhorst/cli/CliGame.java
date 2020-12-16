package de.techfak.se.lwalkenhorst.cli;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.Bounds;
import de.techfak.se.lwalkenhorst.game.Color;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.Tile;
import de.techfak.se.lwalkenhorst.game.TurnFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliGame {

    private final TurnFactory turnFactory = new TurnFactory();
    private final Map<Color, Character> colorMap = new HashMap<>();
    private Game game;

    public CliGame() {
        this.colorMap.put(Color.RED, 'r');
        this.colorMap.put(Color.BLUE, 'b');
        this.colorMap.put(Color.GREEN, 'g');
        this.colorMap.put(Color.ORANGE, 'o');
        this.colorMap.put(Color.YELLOW, 'y');
    }

    public void play(final Game game) {
        this.game = game;
        System.out.println("Welcome to Encore!");
        printBoard();
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                final String input = scanner.nextLine();
                if (input.isEmpty() || input.isBlank()) {
                    return;
                } else {
                    game.applyTurn(turnFactory.parseTurn(input));
                    printBoard();
                }
            } catch (InvalidTurnException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printBoard() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  A B C D E F G H I J K L M N O\n");
        final Board board = game.getBoard();
        final Bounds bounds = board.getBounds();
        for (int row = 0; row < bounds.getRows(); row++) {
            stringBuilder.append(row + 1);
            for (int col = 0; col < bounds.getColumns(); col++) {
                Tile tile = board.getTileAt(row, col);
                char colorChar = colorMap.get(tile.getColor());
                if (tile.isCrossed()) {
                    colorChar = Character.toUpperCase(colorChar);
                }
                stringBuilder.append(" ").append(colorChar);
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }
}