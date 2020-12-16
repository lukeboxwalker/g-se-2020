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
 * Wenn der Zug ein Feld enthält, das bereits angekreuzt ist, dann ist der Zug nicht valide.
 * <p>
 * Äquivalenzklassen:
 * <p>
 * Normalablauf:
 * Die angekreuzten Felder waren alle noch nicht angekreuzt und können nun angekreuzt werden.
 * <p>
 * Eingabefehler #1:
 * Die angekreuzten Felder liegen auf bereits angekreuzten, d.h. mindestens ein Feld das angekreuzt werden soll ist
 * bereits angekreuzt.
 */
class NotCrossedValidationTest {

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
     * Akzeptanzkriterium: Wenn der Zug ein Feld enthält, das bereits angekreuzt ist, dann ist der Zug nicht valide.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder waren alle noch nicht angekreuzt und können nun
     * angekreuzt werden (Normalablauf)
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
     * Zug: C3,C4,B4
     */
    @Test
    void testAllNotCrossed() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Wenn der Zug ein Feld enthält, das bereits angekreuzt ist, dann ist der Zug nicht valide.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder liegen auf bereits angekreuzten, d.h. mindestens
     * ein Feld das angekreuzt werden soll ist bereits angekreuzt. (Eingabefehler #1)
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
     * Zug: D3,D4,E3
     */
    @Test
    void testTilesAlreadyCrossed() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("D3,D4,E3");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        Assertions.assertFalse(game.getBoard().getTileAt(3, 3).isCrossed());
    }
}
