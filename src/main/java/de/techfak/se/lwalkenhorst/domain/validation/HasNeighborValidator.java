package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class HasNeighborValidator implements BoardValidator {

    private final Board board;

    public HasNeighborValidator(final Board board) {
        this.board = board;
    }

    private boolean isNeighborCrossed(final Position position) {
        return board.inBounds(position) && board.getTileAt(position).isCrossed();
    }

    @Override
    public boolean validate(final List<Position> positions) {
        boolean validGroup = false;
        for (final Position position : positions) {
            if (position.getPosX() == board.getStartColumn()) {
                validGroup = true;
                break;
            }
            if (isNeighborCrossed(position.add(-1, 0))
                    || isNeighborCrossed(position.add(1, 0))
                    || isNeighborCrossed(position.add(0, -1))
                    || isNeighborCrossed(position.add(0, 1))) {
                validGroup = true;
                break;
            }
        }
        return validGroup;
    }
}
