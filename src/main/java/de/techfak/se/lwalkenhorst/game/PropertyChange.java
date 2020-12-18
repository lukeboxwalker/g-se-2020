package de.techfak.se.lwalkenhorst.game;

public enum PropertyChange {

    SCORE("score"),
    ROUND("round"),
    FINISHED("finished");

    private final String propertyName;

    PropertyChange(final String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
