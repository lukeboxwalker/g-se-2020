package de.techfak.se.lwalkenhorst.game;

public enum PropertyChange {

    POINTS("points");

    private final String propertyName;

    PropertyChange(final String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
