package de.techfak.se.lwalkenhorst.game;

import java.util.Objects;

public class Bounds {
    private final int rows;
    private final int columns;

    public Bounds(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Bounds bounds = (Bounds) other;
        return rows == bounds.rows && columns == bounds.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }

    @Override
    public String toString() {
        return "Bounds{" +
            "rows=" + rows +
            ", columns=" + columns +
            '}';
    }
}
