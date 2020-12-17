package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllCrossesGroupedValidation implements TurnValidation {

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        if (tilePositions.isEmpty()) {
            return;
        }
        if (!checkGroup(new HashSet<>(tilePositions), tilePositions.get(0)).isEmpty()) {
            throw new InvalidTurnException("Positions not connect to a group");
        }
    }

    private Set<TilePosition> checkGroup(final Set<TilePosition> tilePositions, final TilePosition root) {
        tilePositions.remove(root);
        TilePosition tilePosition = root.left();
        if (tilePositions.contains(tilePosition)) {
            checkGroup(tilePositions, tilePosition);
        }
        tilePosition = root.right();
        if (tilePositions.contains(tilePosition)) {
            checkGroup(tilePositions, tilePosition);
        }
        tilePosition = root.above();
        if (tilePositions.contains(tilePosition)) {
            checkGroup(tilePositions, tilePosition);
        }
        tilePosition = root.down();
        if (tilePositions.contains(tilePosition)) {
            checkGroup(tilePositions, tilePosition);
        }
        return tilePositions;
    }
}
