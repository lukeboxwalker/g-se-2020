package de.techfak.se.template.domain;

import java.util.Arrays;
import java.util.List;

public class Board {
    private final Tile[][] tiles;
    private int startColumn;

    public Board(final Tile[][] tiles) {
        this.tiles = tiles;
        startColumn = 7;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Color getColorAt(Position position) {
        return tiles[position.getX()][position.getY()].getColor();
    }

    private Tile getTile(Position position) {
        return tiles[position.getX()][position.getY()];
    }

    private boolean checkBounds(Position position) {
        if (position.getX() >= tiles.length || position.getX() < 0) {
            return false;
        } else return position.getY() < tiles[0].length && position.getY() >= 0;
    }

    private boolean checkBounds(List<Position> positions) {
        for (Position position : positions) {
            //Check if position is out of bounds
            if (!checkBounds(position)) {
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
                if (root.add(-1, 0).equals(position) || root.add(1, 0).equals(position) ||
                        root.add(0, -1).equals(position) || root.add(0, 1).equals(position)) {
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
            if (position.getX() == startColumn) {
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

    public boolean cross(List<Position> positions) {
        if (checkBounds(positions) && isGroup(positions) && hasNeighborOrStartColumn(positions)) {
            for (Position position : positions) {
                getTile(position).cross();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.deepToString(tiles) +
                '}';
    }
}
