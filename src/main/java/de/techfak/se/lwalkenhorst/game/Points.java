package de.techfak.se.lwalkenhorst.game;

import java.util.Objects;

public class Points {
    private final int value;

    public Points() {
        this(0);
    }

    public Points(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Points points = (Points) other;
        return value == points.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
