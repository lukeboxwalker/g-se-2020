package de.techfak.se.template.domain;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
        return tiles.length == 0 ? 0 : tiles[0].length;
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
        if (positions.size() <= 1) {
            return true;
        }
        boolean hasNeighbor;
        for (final Position root : positions) {
            hasNeighbor = false;
            for (final Position position : positions) {
                if (root.add(-1, 0).equals(position) || root.add(1, 0).equals(position)
                        || root.add(0, -1).equals(position) || root.add(0, 1).equals(position)) {
                    hasNeighbor = true;
                    break;
                }
            }
            if (!hasNeighbor) {
                return false;
            }
        }
        return true;
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
