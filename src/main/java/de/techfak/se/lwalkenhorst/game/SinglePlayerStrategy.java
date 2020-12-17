package de.techfak.se.lwalkenhorst.game;

public class SinglePlayerStrategy implements GameStrategy {

    private final Dice dice = new Dice();
    private DiceResult diceResult;

    @Override
    public void play() {
        this.rollDice();
    }

    @Override
    public void rollDice() {
        diceResult = dice.roll();
    }

    @Override
    public DiceResult getDiceResult() {
        return diceResult;
    }
}
