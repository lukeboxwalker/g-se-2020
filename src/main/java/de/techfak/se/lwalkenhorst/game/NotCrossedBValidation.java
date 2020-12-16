package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class NotCrossedBValidation implements TurnValidation {

    private final Board board;

    public NotCrossedBValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<CellPosition> cellPositions) throws InvalidTurnException {
        for (final CellPosition cellPosition : cellPositions) {
            if (board.getTileAt(cellPosition).isCrossed()) {
                throw new InvalidTurnException("Position: " + cellPosition + " is already crossed");
            }
        }
    }
}
