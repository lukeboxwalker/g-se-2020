package de.techfak.se.lwalkenhorst.domain;

import java.util.List;

public interface GameObserver {

    void onGameStart(final Board board);

    void onDiceRoll(final DiceResult diceResult);

    void onTilesCross(final List<Position> positions);

    void onGameEnd(final int points);
}
