package de.techfak.se.lwalkenhorst.game;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BoardImpl implements Board {
    private static final int DEFAULT_START_COL = 7;
    private final int startColumn;

    private final TileImpl[][] tiles;
    private final Bounds bounds;

    public BoardImpl(final TileImpl[][] tiles, final Bounds bounds) {
        this.tiles = tiles.clone();
        this.bounds = bounds;
        this.startColumn = DEFAULT_START_COL;
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
    public Iterator<Tile> iterator() {
        return Arrays.stream((Tile[][]) tiles).flatMap(Arrays::stream).iterator();
    }

    public void cross(final List<CellPosition> cellPositions) {
        cellPositions.forEach(position -> getTileAt(position).cross());
    }

    @Override
    public String toString() {
        return "AbstractBoard{" +
            "tiles=" + Arrays.deepToString(tiles) +
            '}';
    }
}
