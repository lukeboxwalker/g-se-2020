package de.techfak.se.lwalkenhorst.game;

public enum TileColor {
    GREEN() {
        @Override
        public boolean matches(final DiceColorFace colorFace) {
            return colorFace == DiceColorFace.GREEN;
        }
    },
    YELLOW() {
        @Override
        public boolean matches(final DiceColorFace colorFace) {
            return colorFace == DiceColorFace.YELLOW;
        }
    },
    RED() {
        @Override
        public boolean matches(final DiceColorFace colorFace) {
            return colorFace == DiceColorFace.RED;
        }
    },
    BLUE() {
        @Override
        public boolean matches(final DiceColorFace colorFace) {
            return colorFace == DiceColorFace.BLUE;
        }
    },
    ORANGE() {
        @Override
        public boolean matches(final DiceColorFace colorFace) {
            return colorFace == DiceColorFace.ORANGE;
        }
    };

    public abstract boolean matches(final DiceColorFace colorFace);
}
