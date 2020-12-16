package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;
import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Akzeptanzkriterium:
 * Der Zug ist nur dann valide, wenn jedes weitere Kreuz im selben farbigen Bereich wie das erste Kreuz liegt.
 * <p>
 * Äquivalenzklassen:
 * <p>
 * Normalablauf:
 * Die angekreuzten Felder haben alle die selbe Farbe.
 * <p>
 * Eingabefehler #1:
 * Die angekreuzten Felder haben nicht die selbe Farbe, d.h. mindestens ein Feld hat eine andere Farbe als alle
 * anderen Felder.
 */
class SameColorValidationTest {

    private static final List<String> TEST_BOARD = List.of(
        "gggyyyygbbboyyy",
        "ogygyyooRbboogg",
        "bgrGGGGRRRYyogg",
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
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn jedes weitere Kreuz im selben farbigen Bereich
     * wie das erste Kreuz liegt.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder haben alle die selbe Farbe (Normalablauf)
     * Ausgangszustand:
     * <p>
     * / A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R Y y o g g
     * 4 b r r g o o b b g g Y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     * <p>
     * Zug: H4,H5,G5,G4
     */
    @Test
    void testAllSameColor() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H4,H5,G5,G4");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn jedes weitere Kreuz im selben farbigen Bereich
     * wie das erste Kreuz liegt.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder haben nicht die selbe Farbe, d.h. mindestens ein Feld hat
     * eine andere Farbe als alle anderen Felder. (Eingabefehler #1)
     * Ausgangszustand:
     * <p>
     * / A B C D E F G H I J K L M N O
     * 1 g g g y y y y g b b b o y y y
     * 2 o g y g y y o o R b b o o g g
     * 3 b g r G G G G R R R Y y o g g
     * 4 b r r g o o b b g g Y y o r b
     * 5 r o o o o r b b o o o r r r r
     * 6 r b b r r r r y y o r b b b o
     * 7 y y b b b b r y y y g g g o o
     * <p>
     * Zug: F1,F2,G1,G2
     */
    @Test
    void testNotAllSameColor() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F1,F2,G1,G2");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }

}
