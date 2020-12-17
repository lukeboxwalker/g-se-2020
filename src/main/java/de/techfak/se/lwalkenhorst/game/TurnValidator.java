package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.ArrayList;
import java.util.List;

public class TurnValidator {

    private final List<TurnValidation> turnValidations = new ArrayList<>();
    private final Board board;

    public TurnValidator(final Board board) {
        this.board = board;
        turnValidations.add(new AllSameColorValidation(board));
        turnValidations.add(new NotCrossedBValidation(board));
        turnValidations.add(new CorrectStartCrossValidation(board));
        turnValidations.add(new AllCrossesGroupedValidation());
    }

    public void validateTurn(final Turn turn) throws InvalidTurnException {
        final List<TilePosition> tilePositions = turn.getPositionsToCross();
        for (final TilePosition tilePosition : tilePositions) {
            if (!tilePosition.isInside(board.getBounds())) {
                throw new InvalidTurnException("Position: " + tilePosition + "is Not Found on the board");
            }
        }
        for (final TurnValidation turnValidation : turnValidations) {
            turnValidation.validate(tilePositions);
        }
    }
}
