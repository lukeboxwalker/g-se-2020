package de.techfak.se.lwalkenhorst.game;

public class TileImpl implements Tile {
    private final TileColor tileColor;
    private boolean crossed;

    public TileImpl(final TileColor tileColor) {
        this.tileColor = tileColor;
    }

    public void cross() {
        this.crossed = true;
    }

    @Override
    public TileColor getColor() {
        return tileColor;
    }

    @Override
    public boolean isCrossed() {
        return crossed;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "color=" + tileColor +
                ", crossed=" + crossed +
                '}';
    }
}
