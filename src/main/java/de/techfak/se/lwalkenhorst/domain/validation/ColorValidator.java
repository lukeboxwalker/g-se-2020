package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Color;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class ColorValidator implements DiceValidator<Color> {

    private final Board board;

    public ColorValidator(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(final List<Position> positions, final List<Color> possiblePicks) {
        Color color = null;
        for (final Position position : positions) {
            if (!board.inBounds(position)) {
                return false;
            }
            if (color == null) {
                color = board.getTileAt(position).getColor();
                if (!possiblePicks.contains(color)) {
                    return false;
                }
            } else if (color != board.getTileAt(position).getColor()) {
                return false;
            }
        }
        return true;
    }
}
