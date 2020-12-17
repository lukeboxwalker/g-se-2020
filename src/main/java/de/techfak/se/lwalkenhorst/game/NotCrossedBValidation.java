package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class NotCrossedBValidation implements TurnValidation {

    private final Board board;

    public NotCrossedBValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        for (final TilePosition tilePosition : tilePositions) {
            if (board.getTileAt(tilePosition).isCrossed()) {
                throw new InvalidTurnException("Position: " + tilePosition + " is already crossed");
            }
        }
    }
}
