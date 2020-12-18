package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.ArrayList;
import java.util.List;

public class DiceTurnValidator extends TurnValidator {

    private final List<DiceValidation> diceValidations = new ArrayList<>();

    public DiceTurnValidator(final Board board) {
        super(board);
        diceValidations.add(new DiceColorValidation(board));
        diceValidations.add(new DiceNumberValidation());
    }

    @Override
    public void validateTurn(final Turn turn, final DiceResult diceResult) throws InvalidTurnException {
        super.validateTurn(turn, diceResult);
        for (final DiceValidation diceValidation : diceValidations) {
            diceValidation.validate(turn.getPositionsToCross(), diceResult);
        }
    }
}
