package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllCrossesGroupedValidation implements TurnValidation {

    @Override
    public void validate(final List<CellPosition> cellPositions) throws InvalidTurnException {
        if (cellPositions.isEmpty()) {
            return;
        }
        if (!checkGroup(new HashSet<>(cellPositions), cellPositions.get(0)).isEmpty()) {
            throw new InvalidTurnException("Positions not connect to a group");
        }
    }

    private Set<CellPosition> checkGroup(final Set<CellPosition> cellPositions, final CellPosition root) {
        cellPositions.remove(root);
        CellPosition cellPosition = root.left();
        if (cellPositions.contains(cellPosition)) {
            checkGroup(cellPositions, cellPosition);
        }
        cellPosition = root.right();
        if (cellPositions.contains(cellPosition)) {
            checkGroup(cellPositions, cellPosition);
        }
        cellPosition = root.above();
        if (cellPositions.contains(cellPosition)) {
            checkGroup(cellPositions, cellPosition);
        }
        cellPosition = root.down();
        if (cellPositions.contains(cellPosition)) {
            checkGroup(cellPositions, cellPosition);
        }
        return cellPositions;
    }
}
