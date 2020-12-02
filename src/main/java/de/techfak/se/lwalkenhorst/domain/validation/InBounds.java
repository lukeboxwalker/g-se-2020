package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class InBounds implements TurnValidator {

    private final Board board;

    public InBounds(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(List<Position> positions) {
        for (final Position position : positions) {
            if (!board.inBounds(position)) {
                return false;
            }
        }
        return true;
    }
}
