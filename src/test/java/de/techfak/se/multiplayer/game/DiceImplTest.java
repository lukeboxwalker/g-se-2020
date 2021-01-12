package de.techfak.se.multiplayer.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

class DiceImplTest {

    @Test
    void testDiceRollKnownSeed() {
        final Dice dice = new DiceImpl(100);
        final DiceResult diceResult = dice.roll();

        Assertions.assertEquals(diceResult.getColors(), Arrays.asList(Color.GREEN, Color.ORANGE, Color.YELLOW));
        Assertions.assertEquals(diceResult.getNumbers(), Arrays.asList(Number.ONE, Number.FOUR, Number.TWO));
    }

    @ParameterizedTest
    @ValueSource(longs = {1_238_423, 3_123_123, 5_213_174, -37_545_742, -1_346_345, Long.MAX_VALUE, Long.MIN_VALUE})
    void testDifferentDiceSameSeed(final long seed) {
        final Dice dice1 = new DiceImpl(seed);
        final Dice dice2 = new DiceImpl(seed);

        Assertions.assertEquals(dice1.roll(), dice2.roll());
    }
}
