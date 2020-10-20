package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.NotCrossedValidator;
import de.techfak.se.lwalkenhorst.domain.validation.GroupValidator;
import de.techfak.se.lwalkenhorst.domain.validation.HasNeighborValidator;
import de.techfak.se.lwalkenhorst.domain.validation.BoardValidator;

import java.util.ArrayList;
import java.util.List;

public class Board extends AbstractBoard<Tile> {
    private static final int DEFAULT_START_COL = 7;

    private final int[] pointsPerCol;
    private final List<BoardValidator> validators;
    private final int startColumn;

    public Board(final Tile[][] tiles) {
        super(tiles);
        this.validators = new ArrayList<>();
        this.startColumn = DEFAULT_START_COL;
        this.pointsPerCol = new int[]{5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};

        validators.add(new NotCrossedValidator(this));
        validators.add(new GroupValidator());
        validators.add(new HasNeighborValidator(this));
    }

    public int getStartColumn() {
        return startColumn;
    }

    public boolean cross(final List<Position> positions) {
        for (final BoardValidator validator : validators) {
            if (!validator.validate(positions)) {
                return false;
            }
        }
        for (final Position position : positions) {
            getTileAt(position).cross();
        }
        return true;
    }

    public boolean isColumnFull(final int col) {
        if (inBounds(col, 0)) {
            boolean isFull = true;
            for (int row = 0; row < getLengthY(); row++) {
                if (!getTileAt(col, row).isCrossed()) {
                    isFull = false;
                    break;
                }
            }
            return isFull;
        }
        return false;
    }

    public int getPointsForCol(final int col) {
        if (inBounds(new Position(col ,0))) {
            return pointsPerCol[col];
        } else {
            return 0;
        }
    }
}
