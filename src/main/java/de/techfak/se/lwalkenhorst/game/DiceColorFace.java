package de.techfak.se.lwalkenhorst.game;

public enum DiceColorFace {
    GREEN() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.GREEN;
        }
    },
    YELLOW() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.YELLOW;
        }
    },
    RED() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.RED;
        }
    },
    BLUE() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.BLUE;
        }
    },
    ORANGE() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.ORANGE;
        }
    },
    JOKER() {
        @Override
        public boolean matches(final TileColor color) {
            return true;
        }
    };

    public abstract boolean matches(final TileColor color);
}
