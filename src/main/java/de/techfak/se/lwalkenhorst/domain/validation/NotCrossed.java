package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class NotCrossed implements TrunValidator {

    private final Board board;

    public NotCrossed(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(final List<Position> positions) {
        for (final Position position : positions) {
            if (board.getTileAt(position).isCrossed()) {
                return false;
            }
        }
        return true;
    }
}
