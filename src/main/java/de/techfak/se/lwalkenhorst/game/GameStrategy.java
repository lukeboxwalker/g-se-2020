package de.techfak.se.lwalkenhorst.game;

public interface GameStrategy {

    DiceResult start();

    DiceResult rollDice();
}
