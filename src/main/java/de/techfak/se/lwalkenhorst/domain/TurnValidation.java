package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.exception.InvalidTurnException;

import java.util.List;

public interface TurnValidation {

    void validate(final List<CellPosition> cellPositions) throws InvalidTurnException;
}
