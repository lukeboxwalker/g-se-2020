package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.ColorValidator;
import de.techfak.se.lwalkenhorst.domain.validation.NumberValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Observable {




    protected final List<GameObserver> gameObservers;

    private ColorValidator colorValidator;
    private NumberValidator numberValidator;
    private final Map<Color, Boolean> fullColors;

    protected final DiceResult diceResult;
    protected final Dice<Color> colorDice;
    protected final Dice<Integer> numberDice;

    protected Board board;
    protected int round;

    public Game(Board board) {
        this.setBoard(board);
        this.gameObservers = new ArrayList<>();

        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(1, 2, 3, 4, 5));
        this.diceResult = new DiceResult();
        this.fullColors = new HashMap<>();

        this.round = 1;
        Arrays.stream(Color.values()).forEach(color -> fullColors.put(color, true));
    }

    protected final void setBoard(Board board) {
        this.board = board;
        this.colorValidator = new ColorValidator(this.board);
        this.numberValidator = new NumberValidator();
    }

    public void start() {
        this.gameObservers.forEach(gameObserver -> gameObserver.onGameStart(board));
        rollDice();
    }

    public void pass() {
        this.rollDice();
    }

    public boolean crossTiles(final List<Position> positions) {
        final List<Position> clone = new ArrayList<>(positions);
        if (!numberValidator.validate(clone, diceResult.getRolledNumbers())
                || !colorValidator.validate(clone, diceResult.getRolledColors())) {
            return false;
        }

        if (board.cross(clone)) {
            this.gameObservers.forEach(gameObserver -> gameObserver.onTilesCross(clone));
            final int points = calculatePoints();
            if (isGameFinished()) {
                this.gameObservers.forEach(gameObserver -> gameObserver.onGameEnd(points));
            } else {
                rollDice();
            }
            return true;
        } else {
            return false;
        }
    }

    public void rollDice() {
        diceResult.setRolledColors(colorDice.rollDice(3));
        diceResult.setRolledNumbers(numberDice.rollDice(3));
        this.gameObservers.forEach(gameObserver -> gameObserver.onDiceRoll(diceResult));
    }

    protected int calculatePoints() {
        int points = countFullColors() * 5;
        final List<Integer> fullCols = new ArrayList<>();
        for (int x = 0; x < board.getLengthX(); x++) {
            if (board.isColumnFull(x)) {
                fullCols.add(x);
                points += board.getPointsForCol(x);
            }
        }
        int finalPoints = points;
        this.gameObservers.forEach(gameObserver -> gameObserver.onPointsChange(finalPoints, fullCols));
        return points;
    }

    protected boolean isGameFinished() {
        int fullColorsCount = countFullColors();
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

    @Override
    public void addListener(final GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    @Override
    public void removeListener(final GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }
}
