package de.techfak.se.lwalkenhorst.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Observable {

    private static final int FIVE = 5;

    private final List<GameObserver> gameObservers;
    private final Map<Color, Boolean> fullColors;

    private Board board;

    public Game(final Board board) {
        this.setBoard(board);
        this.gameObservers = new ArrayList<>();
        this.fullColors = new HashMap<>();

        Arrays.stream(Color.values()).forEach(color -> fullColors.put(color, true));
    }

    public final void setBoard(final Board board) {
        this.board = board;
    }

    public void start() {
        this.gameObservers.forEach(gameObserver -> gameObserver.onGameStart(board));
    }

    public boolean crossTiles(final List<Position> positions) {
        final List<Position> clone = new ArrayList<>(positions);
        if (board.cross(clone)) {
            this.gameObservers.forEach(gameObserver -> gameObserver.onTilesCross(clone));
            final int points = calculatePoints();
            if (isGameFinished()) {
                this.gameObservers.forEach(gameObserver -> gameObserver.onGameEnd(points));
            }
            return true;
        } else {
            return false;
        }
    }

    public int calculatePoints() {
        int points = countFullColors() * FIVE;
        final List<Integer> fullCols = new ArrayList<>();
        for (int x = 0; x < board.getLengthX(); x++) {
            if (board.isColumnFull(x)) {
                fullCols.add(x);
                points += board.getPointsForCol(x);
            }
        }
        final int finalPoints = points;
        this.gameObservers.forEach(gameObserver -> gameObserver.onPointsChange(finalPoints, fullCols));
        return points;
    }

    public boolean isGameFinished() {
        final int fullColorsCount = countFullColors();
        return fullColorsCount >= 2;

    }

    private int countFullColors() {
        fullColors.replaceAll((key, value) -> true);
        for (final Tile tile : board) {
            if (!tile.isCrossed()) {
                fullColors.put(tile.getColor(), false);
            }
        }
        int fullColorsCount = 0;
        for (final Map.Entry<Color, Boolean> entry : fullColors.entrySet()) {
            final Boolean value = entry.getValue();
            if (value) {
                fullColorsCount++;
            }
        }
        return fullColorsCount;
    }

    public List<GameObserver> getGameObservers() {
        return gameObservers;
    }

    @Override
    public void addListener(final GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    @Override
    public void removeListener(final GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }
}
