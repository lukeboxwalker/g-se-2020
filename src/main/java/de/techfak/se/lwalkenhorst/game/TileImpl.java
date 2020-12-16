package de.techfak.se.lwalkenhorst.game;

public class TileImpl implements Tile {
    private final Color color;
    private boolean crossed;

    public TileImpl(final Color color) {
        this.color = color;
    }

    public void cross() {
        this.crossed = true;
    }

    public Color getColor() {
        return color;
    }

    public boolean isCrossed() {
        return crossed;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "color=" + color +
                ", crossed=" + crossed +
                '}';
    }
}
