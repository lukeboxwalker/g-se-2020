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

    public int getStartColumn() {
        return startColumn;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private Color getColorAt(Position position) {
        return tiles[position.getPosX()][position.getPosY()].getColor();
    }

    private Tile getTile(Position position) {
        return tiles[position.getPosX()][position.getPosY()];
    }

    private boolean checkBounds(Position position) {
        if (position.getPosX() >= tiles.length || position.getPosX() < 0) {
            return false;
        } else {
            return position.getPosY() < tiles[0].length && position.getPosY() >= 0;
        }
    }

    private boolean checkBounds(List<Position> positions) {
        for (Position position : positions) {
            if (!checkBounds(position)) {
                return false;
            }
            if (getTile(position).isCrossed()) {
                return false;
            }
        }
        return true;
    }

    private boolean isGroup(List<Position> positions) {
        boolean hasNeighbor;
        if (positions.size() <= 1) {
            return true;
        }
        for (Position root : positions) {
            hasNeighbor = false;
            for (Position position : positions) {
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

    private boolean hasNeighborOrStartColumn(List<Position> positions) {
        boolean validGroup = false;
        for (Position position : positions) {
            if (position.getPosX() == startColumn) {
                validGroup = true;
                break;
            }
            Position neighbor = position.add(-1, 0);
            if (checkBounds(neighbor) && getTile(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(1, 0);
            if (checkBounds(neighbor) && getTile(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, -1);
            if (checkBounds(neighbor) && getTile(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
            neighbor = position.add(0, 1);
            if (checkBounds(neighbor) && getTile(neighbor).isCrossed()) {
                validGroup = true;
                break;
            }
        }
        return validGroup;
    }

    private boolean isColorValid(List<Position> positions, List<Color> possibleColors) {
        Color color = null;
        for (Position position : positions) {
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

    public boolean cross(List<Position> positions, List<Color> possibleColors) {
        if (checkBounds(positions) && isColorValid(positions, possibleColors)
                && isGroup(positions) && hasNeighborOrStartColumn(positions)) {
            for (Position position : positions) {
                getTile(position).cross();
            }
            return true;
        }
        return false;
    }

    public boolean isColumnFull(int col) {
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
