package de.techfak.se.template.domain;

public class Tile {
    private final Color color;
    private boolean crossed;

    public Tile(Color color) {
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
