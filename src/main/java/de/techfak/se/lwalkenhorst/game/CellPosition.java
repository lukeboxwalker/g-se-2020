package de.techfak.se.lwalkenhorst.game;

import java.util.Objects;

public class CellPosition {
    private final int column;
    private final int row;

    public CellPosition(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public CellPosition above() {
        return new CellPosition(this.row - 1, this.column);
    }

    public CellPosition down() {
        return new CellPosition(this.row + 1, this.column);
    }

    public CellPosition left() {
        return new CellPosition(this.row, this.column - 1);
    }

    public CellPosition right() {
        return new CellPosition(this.row, this.column + 1);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isInside(final Bounds bounds) {
        return this.column >= 0 && this.column < bounds.getColumns()
            && this.row >= 0 && this.row <= bounds.getRows();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CellPosition) {
            final CellPosition cellPosition = (CellPosition) obj;
            return column == cellPosition.column && row == cellPosition.row;
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
