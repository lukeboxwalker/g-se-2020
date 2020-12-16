package de.techfak.se.lwalkenhorst.game;

public interface Board extends Iterable<Tile> {
    Bounds getBounds();

    Tile getTileAt(final CellPosition cellPosition);

    Tile getTileAt(final int row, final int col);

    int getStartColumn();
}
