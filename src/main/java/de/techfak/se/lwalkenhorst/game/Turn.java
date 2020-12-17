package de.techfak.se.lwalkenhorst.game;

import java.util.List;
import java.util.Objects;

public class Turn {
    private final List<TilePosition> positionsToCrosses;

    public Turn(final List<TilePosition> positionsToCrosses) {
        this.positionsToCrosses = positionsToCrosses;
    }

    public List<TilePosition> getPositionsToCross() {
        return positionsToCrosses;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Turn turn = (Turn) other;
        return Objects.equals(positionsToCrosses, turn.positionsToCrosses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionsToCrosses);
    }

    @Override
    public String toString() {
        return "Turn{" +
            "positionsToCross=" + positionsToCrosses +
            '}';
    }
}
