package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.List;

public class NumberValidator implements DiceValidator<Integer> {

    @Override
    public boolean validate(final List<Position> positions, final List<Integer> possiblePicks) {
        return possiblePicks.contains(positions.size());
    }
}
