package de.techfak.se.lwalkenhorst.domain.validation;

import de.techfak.se.lwalkenhorst.domain.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupValidator implements BoardValidator {

    @Override
    public boolean validate(final List<Position> positions) {
        if (positions.isEmpty()) {
            return true;
        }
        return isGroup(new HashSet<>(positions), positions.get(0)).isEmpty();
    }

    private Set<Position> isGroup(final Set<Position> positions, final Position root) {
        positions.remove(root);
        Position position = root.left();
        if (positions.contains(position)) {
            isGroup(positions, position);
        }
        position = root.right();
        if (positions.contains(position)) {
            isGroup(positions, position);
        }
        position = root.up();
        if (positions.contains(position)) {
            isGroup(positions, position);
        }
        position = root.down();
        if (positions.contains(position)) {
            isGroup(positions, position);
        }
        return positions;
    }

}
