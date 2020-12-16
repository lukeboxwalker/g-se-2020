package de.techfak.se.lwalkenhorst.game;

public interface Observable {

    void addListener(GameObserver gameObserver);

    void removeListener(GameObserver gameObserver);
}
