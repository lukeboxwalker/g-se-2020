package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class CorrectStartCrossValidation implements TurnValidation {

    private final Board board;

    public CorrectStartCrossValidation(final Board board) {
        this.board = board;
    }

    private boolean isNeighborCrossed(final TilePosition tilePosition) {
        return tilePosition.isInside(board.getBounds()) && board.getTileAt(tilePosition).isCrossed();
    }

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        if (tilePositions.isEmpty()) {
            return;
        }
        final TilePosition tilePosition = tilePositions.get(0);
        if (tilePosition.getColumn() == board.getStartColumn()) {
            return;
        }
        boolean hasCrossedNeighbor = isNeighborCrossed(tilePosition.left());
        hasCrossedNeighbor |= isNeighborCrossed(tilePosition.right());
        hasCrossedNeighbor |= isNeighborCrossed(tilePosition.above());
        hasCrossedNeighbor |= isNeighborCrossed(tilePosition.down());
        if (!hasCrossedNeighbor) {
            throw new InvalidTurnException("Position: " + tilePosition + " is not in column H or next to a crossed tile");
        }
    }
}
