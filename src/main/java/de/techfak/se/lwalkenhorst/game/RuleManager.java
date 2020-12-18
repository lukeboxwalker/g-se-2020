package de.techfak.se.lwalkenhorst.game;

import java.util.List;

public interface RuleManager {

    List<TileColor> getFullColors();

    List<Integer> getFullColumns();

    int getPointsForCol(final int column);

    boolean isColumnFull(final int column);

    boolean isGameFinished();
}
