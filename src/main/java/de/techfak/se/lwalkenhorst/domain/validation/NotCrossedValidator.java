package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class NotCrossedValidator implements BoardValidator {

    private final Board board;

    public NotCrossedValidator(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(final List<Position> positions) {
        for (final Position position : positions) {
            if (!board.inBounds(position)) {
                return false;
            }
            if (board.getTileAt(position).isCrossed()) {
                return false;
            }
        }
        return true;
    }
}
