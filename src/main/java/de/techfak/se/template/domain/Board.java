package de.techfak.se.template.domain;

import java.util.Arrays;

public class Board {
    private final Tile[][] tiles;

    public Board(final Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.deepToString(tiles) +
                '}';
    }
}
