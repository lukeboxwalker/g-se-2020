package de.techfak.se.lwalkenhorst.domain;

import java.util.List;

public interface GameObserver {

    void onGameStart(final BoardImpl board);

    void onTilesCross(final List<CellPosition> cellPositions);

    void onGameEnd(final int points);

    void onPointsChange(final int points, final List<Integer> fullColumns);
}
