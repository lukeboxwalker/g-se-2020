package de.techfak.se.lwalkenhorst.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Dice {

    private static final int ROLLS_PER_TYP = 3;
    private final Random random;

    public Dice() {
        this.random = new Random();
    }

    public Dice(final long seed) {
        this.random = new Random(seed);
    }

    public DiceResult roll() {
        final List<DiceColorFace> rolledColors = new ArrayList<>();
        final List<DiceNumberFace> rolledNumbers = new ArrayList<>();
        final List<DiceColorFace> colors = Arrays.asList(DiceColorFace.values());
        final List<DiceNumberFace> numbers = Arrays.asList(DiceNumberFace.values());
        for (int i = 0; i < ROLLS_PER_TYP; i++) {
            rolledColors.add(colors.get(random.nextInt(colors.size())));
            rolledNumbers.add(numbers.get(random.nextInt(numbers.size())));
        }
        return new DiceResult(rolledNumbers, rolledColors);
    }
}
