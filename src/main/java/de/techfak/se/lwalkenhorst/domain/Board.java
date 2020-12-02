package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.HasNeighbor;
import de.techfak.se.lwalkenhorst.domain.validation.InBounds;
import de.techfak.se.lwalkenhorst.domain.validation.IsGroup;
import de.techfak.se.lwalkenhorst.domain.validation.NotCrossed;
import de.techfak.se.lwalkenhorst.domain.validation.SameColor;
import de.techfak.se.lwalkenhorst.domain.validation.TurnValidator;

import java.util.ArrayList;
import java.util.List;

public class Board extends AbstractBoard<Tile> {
    private static final int DEFAULT_START_COL = 7;
    private static final int FIVE = 5;
    private static final int THREE = 3;
    private static final int TWO = 2;
    private static final int ONE = 1;

    private final int[] pointsPerCol;
    private final List<TurnValidator> validators;
    private final int startColumn;

    public Board(final Tile[][] tiles) {
        super(tiles);
        this.validators = new ArrayList<>();
        this.startColumn = DEFAULT_START_COL;
        this.pointsPerCol = new int[]{FIVE, THREE, THREE, THREE, TWO, TWO, TWO, ONE,
            TWO, TWO, TWO, THREE, THREE, THREE, FIVE};

        validators.add(new InBounds(this));
        validators.add(new SameColor(this));
        validators.add(new NotCrossed(this));
        validators.add(new HasNeighbor(this));
        validators.add(new IsGroup());
    }

    public int getStartColumn() {
        return startColumn;
    }

    public boolean cross(final List<Position> positions) {
        for (final TurnValidator validator : validators) {
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
        if (inBounds(new Position(col, 0))) {
            return pointsPerCol[col];
        } else {
            return 0;
        }
    }
}
