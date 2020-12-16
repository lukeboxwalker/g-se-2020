package de.techfak.se.lwalkenhorst.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BoardImpl implements Board {
    private static final int DEFAULT_START_COL = 7;
    private static final int FIVE = 5;
    private static final int THREE = 3;
    private static final int TWO = 2;
    private static final int ONE = 1;

    private final int[] pointsPerCol;
    private final int startColumn;

    private final TileImpl[][] tiles;
    private final Bounds bounds;

    public BoardImpl(final TileImpl[][] tiles, final Bounds bounds) {
        this.tiles = tiles;
        this.bounds = bounds;

        this.startColumn = DEFAULT_START_COL;
        this.pointsPerCol = new int[]{FIVE, THREE, THREE, THREE, TWO, TWO, TWO, ONE,
            TWO, TWO, TWO, THREE, THREE, THREE, FIVE};
    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public TileImpl getTileAt(final int row, final int col) {
        return tiles[row][col];
    }

    @Override
    public TileImpl getTileAt(final CellPosition cellPosition) {
        return getTileAt(cellPosition.getRow(), cellPosition.getColumn());
    }

    @Override
    public int getStartColumn() {
        return startColumn;
    }


    @Override
    public String toString() {
        return "AbstractBoard{" +
            "tiles=" + Arrays.deepToString(tiles) +
            '}';
    }

    public void cross(final List<CellPosition> cellPositions) {
        cellPositions.forEach(position -> getTileAt(position).cross());
    }

    public boolean isColumnFull(final int col) {
        if (new CellPosition(col, 0).isInside(this.getBounds())) {
            boolean isFull = true;
            for (int row = 0; row < getBounds().getRows(); row++) {
                if (!getTileAt(new CellPosition(col, row)).isCrossed()) {
                    isFull = false;
                    break;
                }
            }
            return isFull;
        }
        return false;
    }

    public int getPointsForCol(final int col) {
        if (new CellPosition(col, 0).isInside(this.getBounds())) {
            return pointsPerCol[col];
        } else {
            return 0;
        }
    }

    @Override
    public Iterator<Tile> iterator() {
        return Arrays.stream((Tile[][]) tiles).flatMap(Arrays::stream).iterator();
    }
}
