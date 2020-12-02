package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IsGroup implements TrunValidator {

    @Override
    public boolean validate(final List<Position> positions) {
        if (positions.isEmpty()) {
            return true;
        }
        return checkGroup(new HashSet<>(positions), positions.get(0)).isEmpty();
    }

    private Set<Position> checkGroup(final Set<Position> positions, final Position root) {
        positions.remove(root);
        Position position = root.left();
        if (positions.contains(position)) {
            checkGroup(positions, position);
        }
        position = root.right();
        if (positions.contains(position)) {
            checkGroup(positions, position);
        }
        position = root.up();
        if (positions.contains(position)) {
            checkGroup(positions, position);
        }
        position = root.down();
        if (positions.contains(position)) {
            checkGroup(positions, position);
        }
        return positions;
    }

}
