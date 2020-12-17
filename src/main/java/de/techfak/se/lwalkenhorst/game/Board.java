package de.techfak.se.lwalkenhorst.game;

public interface Board extends Iterable<Tile> {
    Bounds getBounds();

    Tile getTileAt(final TilePosition tilePosition);

    Tile getTileAt(final int row, final int col);

    int getStartColumn();
}
