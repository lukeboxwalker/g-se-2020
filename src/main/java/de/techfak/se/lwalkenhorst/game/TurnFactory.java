package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class TurnFactory {

    private static final String ALPHABET = "ABCDEFGHIJKLMNO";
    private final Pattern pattern = Pattern.compile("([A-O][1-7])(,[A-O][1-7])*");

    public Turn parseTurn(final String input) throws InvalidTurnException {
        if (pattern.matcher(input).matches()) {
            final String[] coordinates = input.split(",");
            final List<TilePosition> positions = new ArrayList<>(coordinates.length);
            for (final String coordinate : coordinates) {
                final int column = ALPHABET.indexOf(String.valueOf(coordinate.charAt(0)).toUpperCase(Locale.ROOT));
                final int row = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
                positions.add(new TilePosition(row, column));
            }
            return createTurn(positions);
        } else {
            throw new InvalidTurnException("Format did not match!");
        }
    }

    public Turn createTurn(final List<TilePosition> crossedPositions) {
       return new Turn(crossedPositions);
    }
}
