package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class AllSameColorValidation implements TurnValidation {

    private final Board board;

    public AllSameColorValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<CellPosition> cellPositions) throws InvalidTurnException {
        TileColor tileColor = null;
        for (final CellPosition cellPosition : cellPositions) {
            if (tileColor == null) {
                tileColor = board.getTileAt(cellPosition).getColor();
            } else if (tileColor != board.getTileAt(cellPosition).getColor()) {
                throw new InvalidTurnException("Positions dont match the same color");
            }
        }
    }
}
