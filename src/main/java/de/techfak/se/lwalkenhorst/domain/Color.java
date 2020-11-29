package de.techfak.se.lwalkenhorst.domain;

public enum Color {
    GREEN('g'),

    YELLOW('y'),

    RED('r'),

    BLUE('b'),

    ORANGE('o');

    private final char identifier;

    Color(final char identifier) {
        this.identifier = identifier;
    }

    public char getIdentifier() {
        return identifier;
    }
}
