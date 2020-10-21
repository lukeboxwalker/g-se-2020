package de.techfak.se.lwalkenhorst.domain;

import java.util.Arrays;
import java.util.Iterator;

public abstract class AbstractBoard<T> implements Iterable<T> {

    private final T[][] tiles;

    public AbstractBoard(final T[][] tiles) {
        this.tiles = tiles;
    }

    public int getLengthX() {
        return tiles.length;
    }

    public int getLengthY() {
        if (tiles.length == 0) {
            return 0;
        }
        return tiles[0].length;
    }

    public T getTileAt(final int posX, final int posY) {
        return tiles[posX][posY];
    }

    public T getTileAt(final Position position) {
        return getTileAt(position.getPosX(), position.getPosY());
    }

    public boolean inBounds(final int posX, final int posY) {
        if (posX >= getLengthX() || posX < 0) {
            return false;
        } else {
            return posY < getLengthY() && posY >= 0;
        }
    }

    public boolean inBounds(final Position position) {
        return inBounds(position.getPosX(), position.getPosY());
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).iterator();
    }

    @Override
    public String toString() {
        return "AbstractBoard{" +
                "tiles=" + Arrays.deepToString(tiles) +
                '}';
    }
}
