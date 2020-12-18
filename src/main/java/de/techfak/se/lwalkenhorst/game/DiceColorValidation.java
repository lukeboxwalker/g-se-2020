package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class DiceColorValidation implements DiceValidation {

    private final Board board;

    public DiceColorValidation(final Board board) {
        this.board = board;
    }

    public void validate(final List<TilePosition> positions, final DiceResult diceResult) throws InvalidTurnException {
        for (final TilePosition position : positions) {
            final TileColor color = board.getTileAt(position).getColor();
            boolean colorMatched = false;
            for (final DiceColorFace colorFace : diceResult.getColors()) {
                if (color.matches(colorFace)) {
                    colorMatched = true;
                }
            }
            if (!colorMatched) {
                throw new InvalidTurnException("No dice is showing the color: " + color);
            }
        }
    }
}
