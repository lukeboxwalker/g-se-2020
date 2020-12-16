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
            final List<CellPosition> positions = new ArrayList<>(coordinates.length);
            for (final String coordinate : coordinates) {
                int column = ALPHABET.indexOf(String.valueOf(coordinate.charAt(0)).toUpperCase(Locale.ROOT));
                int row = Integer.parseInt(String.valueOf(coordinate.charAt(1))) - 1;
                positions.add(CellPosition.of(row, column));
            }
            return new Turn(positions);
        } else {
            throw new InvalidTurnException("Format did not match!");
        }
    }
}
