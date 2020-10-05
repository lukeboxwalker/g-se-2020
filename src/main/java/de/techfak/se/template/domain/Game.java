package de.techfak.se.template.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game implements Observable {

    private final Board board;
    private final List<PropertyChangeListener> observers;

    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;

    public Game(final Board board) {
        this.board = board;
        this.observers = new ArrayList<>();
        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(1, 2, 3, 4, 5));
    }

    public void start() {
        PropertyChangeEvent startEvent = new PropertyChangeEvent(this, "START", null, board);
        observers.forEach(observer -> observer.propertyChange(startEvent));
        
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
