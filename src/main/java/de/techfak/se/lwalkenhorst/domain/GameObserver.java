package de.techfak.se.lwalkenhorst.domain;

import java.util.List;

public interface GameObserver {

    void onGameStart(final Board board);

    void onTilesCross(final List<Position> positions);

    void onGameEnd(final int points);

    void onPointsChange(final int points, final List<Integer> fullColumns);
}
