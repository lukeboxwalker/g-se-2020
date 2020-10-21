package de.techfak.se.lwalkenhorst.domain;

import java.util.Objects;

public class Position {
    private int posX;
    private int posY;

    public Position(final int posX, final int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Position up() {
        return add(0, -1);
    }

    public Position down() {
        return add(0, 1);
    }

    public Position left() {
        return add(-1, 0);
    }

    public Position right() {
        return add(1, 0);
    }

    public Position add(final Position position) {
        return add(position.getPosX(), position.getPosY());
    }

    public Position add(final int posX, final int posY) {
        return new Position(this.posX + posX, this.posY + posY);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(final int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(final int posY) {
        this.posY = posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Position) {
            final Position position = (Position) obj;
            return posX == position.posX && posY == position.posY;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + posX +
                ", y=" + posY +
                '}';
    }
}
