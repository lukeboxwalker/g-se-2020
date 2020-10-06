package de.techfak.se.lwalkenhorst.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Board implements Iterable<Tile> {
    private static final int DEFAULT_START_COL = 7;

    private final Tile[][] tiles;
    private final int startColumn;

    public Board(final Tile[][] tiles) {
        this.tiles = tiles;
        startColumn = DEFAULT_START_COL;
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

    private Color getColorAt(final Position position) {
        return tiles[position.getPosX()][position.getPosY()].getColor();
    }

    public Tile getTileAt(final int posX, final int posY) {
        return tiles[posX][posY];
    }

    public Tile getTileAt(final Position position) {
        return getTileAt(position.getPosX(), position.getPosY());
    }

    private boolean checkBounds(final Position position) {
        if (position.getPosX() >= tiles.length || position.getPosX() < 0) {
            return false;
        } else {
            return position.getPosY() < tiles[0].length && position.getPosY() >= 0;
        }
    }

    private boolean checkBounds(final List<Position> positions) {
        for (final Position position : positions) {
            if (!checkBounds(position)) {
                return false;
            }
            if (getTileAt(position).isCrossed()) {
                return false;
            }
        }
        return true;
    }

    private boolean isGroup(final List<Position> positions) {
        if (positions.size() <= 0) {
            return true;
        }
        final Set<Position> positionSet = new HashSet<>(positions);
        return isGroup(positionSet, positions.get(0)) && positionSet.isEmpty();
    }

    private boolean isGroup(final Set<Position> positions, final Position root) {
        positions.remove(root);
        Position position = root.add(-1, 0);
        boolean isGroup = true;
        if (positions.contains(position)) {
            isGroup = isGroup(positions, position);
        }
        position = root.add(1, 0);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        position = root.add(0, -1);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        position = root.add(0, 1);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        return isGroup;
    }

    private boolean hasNeighborOrStartColumn(final List<Position> positions) {
        boolean validGroup = false;
        for (final Position position : positions) {
            if (position.getPosX() == startColumn) {
                validGroup = true;
                break;
            }
            Position neighbor = position.add(-1, 0);
            if (checkBounds(neighbor) && getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(1, 0);
            if (checkBounds(neighbor) && getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, -1);
            if (checkBounds(neighbor) && getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, 1);
            if (checkBounds(neighbor) && getTileAt(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
        }
        return validGroup;
    }

    private boolean isColorValid(final List<Position> positions, final List<Color> possibleColors) {
        Color color = null;
        for (final Position position : positions) {
            if (color == null) {
                color = getColorAt(position);
                if (!possibleColors.contains(color)) {
                    return false;
                }
            } else if (color != getColorAt(position)) {
                return false;
            }
        }
        return true;
    }

    public boolean cross(final List<Position> positions, final List<Color> possibleColors) {
        if (checkBounds(positions) && isColorValid(positions, possibleColors)
                && isGroup(positions) && hasNeighborOrStartColumn(positions)) {
            for (final Position position : positions) {
                getTileAt(position).cross();
            }
            return true;
        }
        return false;
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
