package de.techfak.se.lwalkenhorst.cli;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.Bounds;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.Tile;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TurnFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliGame {

    private final TurnFactory turnFactory = new TurnFactory();
    private final Map<TileColor, String> uncrossedColors = new HashMap<>();
    private final Map<TileColor, String> crossedColors = new HashMap<>();
    private Game game;

    public CliGame() {
        this.uncrossedColors.put(TileColor.RED, "\033[0;31mr");
        this.uncrossedColors.put(TileColor.GREEN, "\033[0;32mg");
        this.uncrossedColors.put(TileColor.YELLOW, "\033[0;33my");
        this.uncrossedColors.put(TileColor.BLUE, "\033[0;34mb");
        this.uncrossedColors.put(TileColor.ORANGE, "\033[0;35mo");

        this.crossedColors.put(TileColor.RED, "\033[7m\033[1;31mR");
        this.crossedColors.put(TileColor.GREEN,"\033[7m\033[1;32mG");
        this.crossedColors.put(TileColor.YELLOW, "\033[7m\033[1;33mY");
        this.crossedColors.put(TileColor.BLUE, "\033[7m\033[1;34mB");
        this.crossedColors.put(TileColor.ORANGE, "\033[7m\033[1;35mO");
    }

    public void play(final Game game) {
        this.game = game;
        System.out.println("Welcome to Encore!");
        System.out.println(createBoardString());
        final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {
            System.out.println("Enter your turn: ");
            if (game.getRuleManger().isGameFinished()) {
                System.out.println("Game over. Your points: " + game.getPoints());
                return;
            }
            try {
                final String input = scanner.nextLine();
                if (input.isEmpty() || input.isBlank()) {
                    return;
                } else {
                    game.applyTurn(turnFactory.parseTurn(input));
                    System.out.println(createBoardString());
                    System.out.println("Points: " + game.getPoints());
                }
            } catch (InvalidTurnException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String createBoardString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  A B C D E F G H I J K L M N O\n");
        final Board board = game.getBoard();
        final Bounds bounds = board.getBounds();
        for (int row = 0; row < bounds.getRows(); row++) {
            stringBuilder.append(row + 1);
            for (int col = 0; col < bounds.getColumns(); col++) {
                final Tile tile = board.getTileAt(row, col);
                stringBuilder.append(" ");
                if (tile.isCrossed()) {
                    stringBuilder.append(crossedColors.get(tile.getColor()));
                } else {
                    stringBuilder.append(uncrossedColors.get(tile.getColor()));
                }
                stringBuilder.append("\033[0m");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
