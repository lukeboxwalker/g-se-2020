package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class NotCrossedValidation implements TurnValidation {

    private final Board board;

    public NotCrossedValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        for (final TilePosition tilePosition : tilePositions) {
            if (board.getTileAt(tilePosition).isCrossed()) {
                throw new InvalidTurnException(tilePosition + " is already crossed");
            }
        }
    }
}
