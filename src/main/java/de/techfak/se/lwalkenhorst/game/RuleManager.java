package de.techfak.se.lwalkenhorst.game;

import java.util.List;

public interface RuleManager {

    List<Color> getFullColors();

    int getPointsForCol(final int column);

    boolean isColumnFull(final int column);
}
