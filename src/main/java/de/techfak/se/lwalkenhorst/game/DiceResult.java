package de.techfak.se.lwalkenhorst.game;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

/**
 * Represents a dice roll result.
 * <p>
 * Contains the rolled numbers as well as the rolled colors.
 */
public class DiceResult {

    private final List<DiceNumberFace> rolledNumbers;

    private final List<DiceColorFace> rolledColors;

    public DiceResult(final List<DiceNumberFace> rolledNumbers, final List<DiceColorFace> rolledColors) {
        this.rolledNumbers = unmodifiableList(rolledNumbers);
        this.rolledColors = unmodifiableList(rolledColors);
    }

    public List<DiceNumberFace> getNumbers() {
        return rolledNumbers;
    }

    public List<DiceColorFace> getColors() {
        return rolledColors;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final DiceResult that = (DiceResult) other;
        return Objects.equals(rolledNumbers, that.rolledNumbers)
            && Objects.equals(rolledColors, that.rolledColors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolledNumbers, rolledColors);
    }
}
