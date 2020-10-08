package de.techfak.se.lwalkenhorst.domain;

public interface Observable {

    void addListener(GameObserver gameObserver);

    void removeListener(GameObserver gameObserver);
}
