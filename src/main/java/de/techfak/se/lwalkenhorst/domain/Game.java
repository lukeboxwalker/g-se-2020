package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.exception.InvalidTurnException;

public interface Game {

    void applyTurn(final Turn turn) throws InvalidTurnException;

    RuleManager getRuleManger();

    Board getBoard();
}
