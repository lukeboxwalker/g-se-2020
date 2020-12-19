package de.techfak.se.lwalkenhorst.game;

public enum DiceNumberFace {
    ONE() {
        @Override
        public boolean matches(final int number) {
            return number == 1;
        }
    },
    TWO() {
        @Override
        public boolean matches(final int number) {
            return number == 2;
        }
    },
    THREE() {
        @Override
        public boolean matches(final int number) {
            return number == 3;
        }
    },
    FOUR() {
        @Override
        public boolean matches(final int number) {
            return number == 4;
        }
    },
    FIVE() {
        @Override
        public boolean matches(final int number) {
            return number == 5;
        }
    },
    JOKER() {
        @Override
        public boolean matches(final int number) {
            return number > 0 && number < 5;
        }
    };

    public abstract boolean matches(final int number);
}
