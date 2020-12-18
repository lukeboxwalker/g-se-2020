package de.techfak.se.lwalkenhorst.game;

public interface TurnValidatorFactory {
    TurnValidator create(final Board board);
}
