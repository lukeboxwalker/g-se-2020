package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;
import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

/**
 * Akzeptanzkriterium:
 * Der Zug ist nur dann valide, wenn nur Positionen innerhalb des Boards angekreuzt werden.
 * <p>
 * Äquivalenzklassen:
 * <p>
 * Normalablauf:
 * Alle Positionen liegen innerhalb des Boards.
 * <p>
 * Eingabefehler #1:
 * Es wird mindestens eine Position angekreuzt mit x größer 14
 * <p>
 * Eingabefehler #2:
 * Es wird mindestens eine Position angekreuzt mit x kleiner 0
 * <p>
 * Eingabefehler #3:
 * Es wird mindestens eine Position angekreuzt mit y größer 6
 * <p>
 * Eingabefehler #4:
 * Es wird mindestens eine Position angekreuzt mit y kleiner 0
 */
class PositionValidationTest {

    private static final List<String> TEST_BOARD = List.of(
        "gggyyyygbbboyYy",
        "OgygyyooRbbooGG",
        "BGRGGGGRRRYYOGG",
        "brrgoobbggYyorb",
        "roooorbbooorrrr",
        "rbbrrrryyorbbbo",
        "yybbbbryyygggoo");

    private final TurnFactory turnFactory = new TurnFactory();
    private Game game;

    @BeforeEach
    void setUp() throws InvalidBoardLayoutException, InvalidFieldException {
        final GameFactory factory = new GameFactory(7, 15);
        game = factory.createGame(TEST_BOARD);
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn nur Positionen innerhalb des Boards angekreuzt werden.
     * <p>
     * Äquivalenzklasse: Alle Positionen liegen innerhalb des Boards (Normalablauf)
     * Ausgangszustand:
     * <p>
     * / A B C D E F G H I J K L M N O
     * 1 g g g y y y y G b b b o y Y y
     * 2 o g y g y y o o R b b o o G G
     * 3 B R R G G G G R R R Y Y O G G
     * 4 b r r g o o b b g g Y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r Y y y g g g o o
     * <p>
     * Zug: N4,N5,M5
     */
    @Test
    void testPositionsInBounds() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn nur Positionen innerhalb des Boards angekreuzt werden.
     * <p>
     * Äquivalenzklasse: Es wird mindestens eine Position angekreuzt mit x größer 14 oder kleiner 0
     * (Eingabefehler #1 #2)
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -22, -144, -6205, Integer.MIN_VALUE, 15, 16, 32, 64, 1001, Integer.MAX_VALUE})
    void testPositionXToLarge(final int column) {
        final Turn turn = turnFactory.createTurn(List.of(new CellPosition(1, column)));
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn nur Positionen innerhalb des Boards angekreuzt werden.
     * <p>
     * Äquivalenzklasse: Es wird mindestens eine Position angekreuzt mit y größer 6 oder kleiner 0
     * (Eingabefehler #3 #4)
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -22, -144, -6205, Integer.MIN_VALUE, 7, 8, 29, 82, 2587, Integer.MAX_VALUE})
    void testPositionYToLarge(final int row) {
        final Turn turn = turnFactory.createTurn(List.of(new CellPosition(row, 7)));
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }
}
