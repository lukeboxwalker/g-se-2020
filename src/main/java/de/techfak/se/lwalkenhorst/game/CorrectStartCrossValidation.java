package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class CorrectStartCrossValidation implements TurnValidation {

    private final Board board;

    public CorrectStartCrossValidation(final Board board) {
        this.board = board;
    }

    private boolean isNeighborCrossed(final CellPosition cellPosition) {
        return cellPosition.isInside(board.getBounds()) && board.getTileAt(cellPosition).isCrossed();
    }

    @Override
    public void validate(final List<CellPosition> cellPositions) throws InvalidTurnException {
        if (cellPositions.isEmpty()) {
            return;
        }
        final CellPosition cellPosition = cellPositions.get(0);
        if (cellPosition.getColumn() == board.getStartColumn()) {
            return;
        }
        boolean hasCrossedNeighbor = isNeighborCrossed(cellPosition.left());
        hasCrossedNeighbor |= isNeighborCrossed(cellPosition.right());
        hasCrossedNeighbor |= isNeighborCrossed(cellPosition.above());
        hasCrossedNeighbor |= isNeighborCrossed(cellPosition.down());
        if (!hasCrossedNeighbor) {
            throw new InvalidTurnException("Position: " + cellPosition + " is not in column H or next to a crossed tile");
        }
    }
}
