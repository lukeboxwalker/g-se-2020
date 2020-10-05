package de.techfak.se.template.domain;

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

    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;

    private final DiceResult diceResult;

    public Game(final Board board) {
        this.board = board;
        this.observers = new ArrayList<>();
        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(1, 2, 3, 4, 5));
        this.diceResult = new DiceResult();

    }

    public void start() {
        PropertyChangeEvent startEvent = new PropertyChangeEvent(this, "START", null, board);
        this.observers.forEach(observer -> observer.propertyChange(startEvent));
        rollDice();
    }

    public boolean crossTiles(final List<Position> positions) {
        if (!diceResult.getRolledNumbers().contains(positions.size())) {
            return false;
        }
        Color color = null;
        for (Position position : positions) {
            if (color == null) {
                color = board.getColorAt(position);
            } else if (color != board.getColorAt(position)){
                return false;
            }
        }

        if (board.cross(positions)) {
            boolean gameFinished = isGameFinished();

            PropertyChangeEvent crossEvent = new PropertyChangeEvent(this, "CROSS_POSITIONS", null, positions);
            this.observers.forEach(observer -> observer.propertyChange(crossEvent));

            if (gameFinished) {
                int points = 0;
                PropertyChangeEvent endEvent = new PropertyChangeEvent(this, "END", null, points);
                this.observers.forEach(observer -> observer.propertyChange(endEvent));
            }

            rollDice();
            return true;
        } else {
            return false;
        }
    }

    public void rollDice() {
        diceResult.setRolledColors(colorDice.rollDice(3));
        diceResult.setRolledNumbers(numberDice.rollDice(3));
        PropertyChangeEvent diceEvent = new PropertyChangeEvent(this, "DICE_RESULT", null, diceResult);
        this.observers.forEach(observer -> observer.propertyChange(diceEvent));
    }

    private boolean isGameFinished() {
        Map<Color, Boolean> fullColors = new HashMap<>();
        Arrays.stream(Color.values()).forEach(color -> fullColors.put(color, true));
        for (Tile tile : board) {
            if (!tile.isCrossed()) {
                fullColors.put(tile.getColor(), false);
            }
        }
        int fullColorsCount = 0;
        for (Map.Entry<Color, Boolean> entry : fullColors.entrySet()) {
            Boolean value = entry.getValue();
            if (value) {
                fullColorsCount++;
            }
        }
        return fullColorsCount >= 2;

    }


    @Override
    public void addPropertyChangeListener(PropertyChangeListener observer) {
        observers.add(observer);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener observer) {
        observers.remove(observer);
    }
}
