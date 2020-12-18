package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class DiceNumberValidation implements DiceValidation {

    @Override
    public void validate(final List<TilePosition> positions, final DiceResult diceResult) throws InvalidTurnException {
        boolean numberMatched = false;
        final int crossedPositions = positions.size();
        for (final DiceNumberFace numberFace : diceResult.getNumbers()) {
            numberMatched |= numberFace.matches(crossedPositions);
        }
        if (!numberMatched) {
            throw new InvalidTurnException("No dice is showing the number of crosses you have chosen");
        }
    }
}
