package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class HasNeighborValidator implements BoardValidator {

    private final Board board;

    public HasNeighborValidator(final Board board) {
        this.board = board;
    }

    @Override
    public boolean validate(final List<Position> positions) {
        boolean validGroup = false;
        for (final Position position : positions) {
            if (position.getPosX() == board.getStartColumn()) {
                validGroup = true;
                break;
            }
            Position neighbor = position.add(-1, 0);
            if (board.inBounds(neighbor) && board.getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(1, 0);
            if (board.inBounds(neighbor) && board.getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, -1);
            if (board.inBounds(neighbor) && board.getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, 1);
            if (board.inBounds(neighbor) && board.getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
        }
        return validGroup;
    }
}
