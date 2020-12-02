package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class HasNeighbor implements TurnValidator {

    private final Board board;

    public HasNeighbor(final Board board) {
        this.board = board;
    }

    private boolean isNeighborCrossed(final Position position) {
        return board.getTileAt(position).isCrossed();
    }

    @Override
    public boolean validate(final List<Position> positions) {
        if (positions.isEmpty()) {
            return true;
        }
        final Position startPosition = positions.get(0);
        if (startPosition.getPosX() == board.getStartColumn()) {
            return true;

        }
        return isNeighborCrossed(startPosition.left()) || isNeighborCrossed(startPosition.right())
                || isNeighborCrossed(startPosition.up()) || isNeighborCrossed(startPosition.down());
    }
}
