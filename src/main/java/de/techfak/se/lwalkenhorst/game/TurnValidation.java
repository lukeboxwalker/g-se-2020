package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public interface TurnValidation {

    void validate(final List<CellPosition> cellPositions) throws InvalidTurnException;
}
