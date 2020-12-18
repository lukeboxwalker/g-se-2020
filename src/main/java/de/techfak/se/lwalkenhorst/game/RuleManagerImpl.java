package de.techfak.se.lwalkenhorst.game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RuleManagerImpl implements RuleManager {

    private static final int POINTS_FOR_FULL_COLOR = 5;
    private static final int POINTS_FOR_COL_H = 1;
    private static final int POINTS_FOR_COL_E_F_G_I_J_K = 2;
    private static final int POINTS_FOR_COL_B_C_D_L_M_N = 3;
    private static final int POINTS_FOR_COL_A_O = 5;

    private static final Set<Integer> COLUMN_H = Set.of(7);
    private static final Set<Integer> COLUMN_E_F_G_I_J_K = Set.of(4, 5, 6, 8, 9, 10);
    private static final Set<Integer> COLUMN_B_C_D_L_M_N = Set.of(1, 2, 3, 11, 12, 13);
    private static final Set<Integer> COLUMN_A_O = Set.of(0, 14);

    private final Map<TileColor, Boolean> fullColorMap;
    private final Map<Integer, Boolean> fullColumnMap;
    private final Board board;

    public RuleManagerImpl(final Board board) {
        this.board = board;
        this.fullColorMap = Arrays.stream(TileColor.values())
            .collect(Collectors.toMap(Function.identity(), color -> false));

        fullColumnMap = new HashMap<>();
        for (int col = 0; col < board.getBounds().getColumns(); col++) {
            fullColumnMap.put(col, false);
        }
    }

    public Points calculatePoints() {
        return new Points(getPointsForFullColors() + getPointsForFullColumns());
    }

    private int getPointsForFullColumns() {
        int points = 0;
        fullColumnMap.replaceAll((key, value) -> true);
        for (int col = 0; col < board.getBounds().getColumns(); col++) {
            fullColumnMap.put(col, isColumnFull(col));
        }
        for (int col = 0; col < board.getBounds().getColumns(); col++) {
            if (fullColumnMap.get(col)) {
                points += getPointsForCol(col);
            }
        }
        return points;
    }

    private int getPointsForFullColors() {
        fullColorMap.replaceAll((key, value) -> true);
        for (final Tile tile : board) {
            if (!tile.isCrossed()) {
                fullColorMap.put(tile.getColor(), false);
            }
        }
        return getFullColors().size() * POINTS_FOR_FULL_COLOR;
    }

    @Override
    public List<Integer> getFullColumns() {
        return fullColumnMap.entrySet()
            .stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public boolean isGameFinished() {
        return getFullColors().size() >= 2;
    }

    @Override
    public List<TileColor> getFullColors() {
        return fullColorMap.entrySet()
            .stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public int getPointsForCol(final int column) {
        if (COLUMN_H.contains(column)) {
            return POINTS_FOR_COL_H;
        }
        if (COLUMN_E_F_G_I_J_K.contains(column)) {
            return POINTS_FOR_COL_E_F_G_I_J_K;
        }
        if (COLUMN_B_C_D_L_M_N.contains(column)) {
            return POINTS_FOR_COL_B_C_D_L_M_N;
        }
        if (COLUMN_A_O.contains(column)) {
            return POINTS_FOR_COL_A_O;
        }
        return 0;
    }

    @Override
    public boolean isColumnFull(final int column) {
        if (new TilePosition(0, column).isInside(board.getBounds())) {
            boolean isFull = true;
            for (int row = 0; row < board.getBounds().getRows(); row++) {
                if (!board.getTileAt(row, column).isCrossed()) {
                    isFull = false;
                    break;
                }
            }
            return isFull;
        }
        return false;
    }
}
