package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.ColorValidator;
import de.techfak.se.lwalkenhorst.domain.validation.NumberValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Observable {

    private final Board board;
    private final List<GameObserver> gameObservers;
    private final int[] pointsPerCol;

    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;
    private final ColorValidator colorValidator;
    private final NumberValidator numberValidator;
    private final Map<Color, Boolean> fullColors;
    private final DiceResult diceResult;

    public Game(final Board board) {
        this.board = board;
        this.gameObservers = new ArrayList<>();
        this.pointsPerCol = new int[]{5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};
        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(1, 2, 3, 4, 5));
        this.diceResult = new DiceResult();
        this.fullColors = new HashMap<>();
        this.colorValidator = new ColorValidator(this.board);
        this.numberValidator = new NumberValidator();
        Arrays.stream(Color.values()).forEach(color -> fullColors.put(color, true));
    }

    public void start() {
        this.gameObservers.forEach(gameObserver -> gameObserver.onGameStart(board));
        rollDice();
    }

    public boolean crossTiles(final List<Position> positions) {
        final List<Position> clone = new ArrayList<>(positions);
        if (!numberValidator.validate(clone, diceResult.getRolledNumbers())
                || !colorValidator.validate(clone, diceResult.getRolledColors())) {
            return false;
        }

        if (board.cross(clone)) {
            this.gameObservers.forEach(gameObserver -> gameObserver.onTilesCross(clone));

            if (isGameFinished()) {
                final int points = calculatePoints();
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

    private int calculatePoints() {
        int points = 10;
        for (int x = 0; x < board.getLengthX(); x++) {
            if (board.isColumnFull(x)) {
                points += this.pointsPerCol[x];
            }
        }
        return points;
    }

    private boolean isGameFinished() {
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
        return fullColorsCount >= 2;

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
