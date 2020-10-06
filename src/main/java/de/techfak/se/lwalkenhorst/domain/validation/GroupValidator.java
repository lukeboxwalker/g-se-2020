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
        final Set<Position> positionSet = new HashSet<>(positions);
        return isGroup(positionSet, positions.get(0)) && positionSet.isEmpty();
    }

    private boolean isGroup(final Set<Position> positions, final Position root) {
        positions.remove(root);
        Position position = root.add(-1, 0);
        boolean isGroup = true;
        if (positions.contains(position)) {
            isGroup = isGroup(positions, position);
        }
        position = root.add(1, 0);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        position = root.add(0, -1);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        position = root.add(0, 1);
        if (positions.contains(position)) {
            isGroup &= isGroup(positions, position);
        }
        return isGroup;
    }

}
