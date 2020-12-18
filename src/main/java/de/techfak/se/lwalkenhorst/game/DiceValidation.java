package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public interface DiceValidation {

    void validate(final List<TilePosition> positions, final DiceResult diceResult) throws InvalidTurnException;
}
