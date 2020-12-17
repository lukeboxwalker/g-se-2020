package de.techfak.se.lwalkenhorst.game;

public interface GameStrategy {

    void play();

    void rollDice();

    DiceResult getDiceResult();
}
