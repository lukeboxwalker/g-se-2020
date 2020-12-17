package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.util.List;

public class AllSameColorValidation implements TurnValidation {

    private final Board board;

    public AllSameColorValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        TileColor tileColor = null;
        for (final TilePosition tilePosition : tilePositions) {
            if (tileColor == null) {
                tileColor = board.getTileAt(tilePosition).getColor();
            } else if (tileColor != board.getTileAt(tilePosition).getColor()) {
                throw new InvalidTurnException("Positions dont match the same color");
            }
        }
    }
}
