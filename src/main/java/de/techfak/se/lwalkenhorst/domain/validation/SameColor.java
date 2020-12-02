package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Color;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class SameColor implements TrunValidator {

    private final Board board;

    public SameColor(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(final List<Position> positions) {
        Color color = null;
        for (final Position position : positions) {
            if (color == null) {
                color = board.getTileAt(position).getColor();
            } else if (color != board.getTileAt(position).getColor()) {
                return false;
            }
        }
        return true;
    }
}
