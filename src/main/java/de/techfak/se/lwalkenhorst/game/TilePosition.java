package de.techfak.se.lwalkenhorst.game;

import java.util.Objects;

public class TilePosition {
    private final int column;
    private final int row;

    public TilePosition(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public TilePosition above() {
        return new TilePosition(this.row - 1, this.column);
    }

    public TilePosition down() {
        return new TilePosition(this.row + 1, this.column);
    }

    public TilePosition left() {
        return new TilePosition(this.row, this.column - 1);
    }

    public TilePosition right() {
        return new TilePosition(this.row, this.column + 1);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isInside(final Bounds bounds) {
        return this.column >= 0 && this.column < bounds.getColumns()
            && this.row >= 0 && this.row < bounds.getRows();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof TilePosition) {
            final TilePosition tilePosition = (TilePosition) obj;
            return column == tilePosition.column && row == tilePosition.row;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Position{" +
            "column=" + column +
            ", row=" + row +
            '}';
    }
}
