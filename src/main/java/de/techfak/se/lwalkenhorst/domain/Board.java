package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.NotCrossedValidator;
import de.techfak.se.lwalkenhorst.domain.validation.GroupValidator;
import de.techfak.se.lwalkenhorst.domain.validation.HasNeighborValidator;
import de.techfak.se.lwalkenhorst.domain.validation.BoardValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable<Tile> {
    private static final int DEFAULT_START_COL = 7;

    private final List<BoardValidator> validators;
    private final Tile[][] tiles;
    private final int startColumn;

    public Board(final Tile[][] tiles) {
        this.tiles = tiles;
        this.validators = new ArrayList<>();
        this.startColumn = DEFAULT_START_COL;

        validators.add(new NotCrossedValidator(this));
        validators.add(new GroupValidator());
        validators.add(new HasNeighborValidator(this));
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

    public int getStartColumn() {
        return startColumn;
    }

    public boolean inBounds(final Position position) {
        if (position.getPosX() >= getLengthX() || position.getPosX() < 0) {
            return false;
        } else {
            return position.getPosY() < getLengthY() && position.getPosY() >= 0;
        }
    }

    public Tile getTileAt(final int posX, final int posY) {
        return tiles[posX][posY];
    }

    public Tile getTileAt(final Position position) {
        return getTileAt(position.getPosX(), position.getPosY());
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
        if (col >= 0 && col < tiles.length) {
            boolean isFull = true;
            for (int row = 0; row < tiles[0].length; row++) {
                if (!tiles[col][row].isCrossed()) {
                    isFull = false;
                    break;
                }
            }
            return isFull;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.deepToString(tiles) +
                '}';
    }

    @Override
    public Iterator<Tile> iterator() {
        return Arrays.stream(tiles).flatMap(Arrays::stream).iterator();
    }
}
