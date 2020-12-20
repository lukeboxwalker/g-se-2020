package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.beans.PropertyChangeListener;

public interface Game {

    void applyTurn(final Turn turn) throws InvalidTurnException;

    RuleManager getRuleManger();

    Score getPoints();

    Board getBoard();

    void play();

    DiceResult getDiceResult();

    void addListener(final PropertyChange propertyChange, final PropertyChangeListener listener);
}
