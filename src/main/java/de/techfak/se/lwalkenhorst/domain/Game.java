package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.ColorValidator;
import de.techfak.se.lwalkenhorst.domain.validation.NumberValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Observable {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    private final List<GameObserver> gameObservers;
    private final Map<Color, Boolean> fullColors;
    private final DiceResult diceResult;
    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;

    private ColorValidator colorValidator;
    private NumberValidator numberValidator;
    private Board board;

    public Game(final Board board) {
        this.setBoard(board);
        this.gameObservers = new ArrayList<>();

        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(ONE, TWO, THREE, FOUR, FIVE));
        this.diceResult = new DiceResult();
        this.fullColors = new HashMap<>();

        Arrays.stream(Color.values()).forEach(color -> fullColors.put(color, true));
    }

    public final void setBoard(final Board board) {
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
        diceResult.setRolledColors(colorDice.rollDice(THREE));
        diceResult.setRolledNumbers(numberDice.rollDice(THREE));
        this.gameObservers.forEach(gameObserver -> gameObserver.onDiceRoll(diceResult));
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

    public DiceResult getDiceResult() {
        return diceResult;
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
