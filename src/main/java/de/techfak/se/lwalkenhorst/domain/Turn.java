package de.techfak.se.lwalkenhorst.domain;

import java.util.List;
import java.util.Objects;

public class Turn {
    private final List<CellPosition> positionsToCrosses;

    public Turn(final List<CellPosition> positionsToCrosses) {
        this.positionsToCrosses = positionsToCrosses;
    }

    public List<CellPosition> getPositionsToCross() {
        return positionsToCrosses;
    }

    @Override
    public boolean equals(Object other) {
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
