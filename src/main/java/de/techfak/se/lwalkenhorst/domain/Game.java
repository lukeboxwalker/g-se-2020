package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.validation.ColorValidator;
import de.techfak.se.lwalkenhorst.domain.validation.NumberValidator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Observable {

    private final Board board;
    private final List<PropertyChangeListener> observers;
    private final int[] pointsPerCol;

    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;
    private final ColorValidator colorValidator;
    private final NumberValidator numberValidator;
    private final Map<Color, Boolean> fullColors;
    private final DiceResult diceResult;

    public Game(final Board board) {
        this.board = board;
        this.observers = new ArrayList<>();
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
        final PropertyChangeEvent startEvent = new PropertyChangeEvent(this, "START", null, board);
        this.observers.forEach(observer -> observer.propertyChange(startEvent));
        rollDice();
    }

    public boolean crossTiles(final List<Position> positions) {
        if (!numberValidator.validate(positions, diceResult.getRolledNumbers())
                || !colorValidator.validate(positions, diceResult.getRolledColors())) {
            return false;
        }

        if (board.cross(positions)) {
            final PropertyChangeEvent crossEvent = new PropertyChangeEvent(this, "CROSS_POSITIONS", null, positions);
            this.observers.forEach(observer -> observer.propertyChange(crossEvent));

            if (isGameFinished()) {
                final int points = calculatePoints();
                final PropertyChangeEvent endEvent = new PropertyChangeEvent(this, "END", null, points);
                this.observers.forEach(observer -> observer.propertyChange(endEvent));
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
        final PropertyChangeEvent diceEvent = new PropertyChangeEvent(this, "DICE_RESULT", null, diceResult);
        this.observers.forEach(observer -> observer.propertyChange(diceEvent));
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
    public void addPropertyChangeListener(final PropertyChangeListener observer) {
        observers.add(observer);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener observer) {
        observers.remove(observer);
    }
}
