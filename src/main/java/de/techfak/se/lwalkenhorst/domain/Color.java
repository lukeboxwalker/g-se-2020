package de.techfak.se.lwalkenhorst.domain;

public enum Color {
    GREEN('g', "green"),
    YELLOW('y', "yellow"),
    RED('r', "red"),
    BLUE('b', "blue"),
    ORANGE('o', "orange");

    private final char identifier;
    private final String fxName;

    Color(final char identifier, final String fxName) {
        this.identifier = identifier;
        this.fxName = fxName;
    }

    public char getIdentifier() {
        return identifier;
    }

    public String getFxName() {
        return fxName;
    }
}
