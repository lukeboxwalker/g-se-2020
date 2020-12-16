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
 * Der Zug ist nur dann valide, wenn das erste Kreuz des Zuges in Spalte H des Spielfelds oder
 * benachbart zu einem bereits vorher im Spielfeld vorhandenen Kreuz ist.
 * <p>
 * Äquivalenzklassen:
 * <p>
 * Normalablauf #1:
 * Das erste Kreuz liegt in Spalte H.
 * <p>
 * Normalablauf #2:
 * Das erste Kreuz grenzt an ein bereits angekreuztes Feld.
 * <p>
 * Eingabefehler #1:
 * Das erste Kreuz liegt weder in Spalte H noch grenzt es an ein bereits angekreuztes Feld an.
 */
class NeighborValidationTest {

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
     * Äquivalenzklasse: Das erste Kreuz liegt in Spalte H (Normalablauf #1)
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
     * Zug: H6,I6,I7
     */
    @Test
    void testStartInColH() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Wenn der Zug ein Feld enthält, das bereits angekreuzt ist, dann ist der Zug nicht valide.
     * <p>
     * Äquivalenzklasse: Das erste Kreuz grenzt an ein bereits angekreuztes Feld. (Normalablauf #2)
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
     * Zug: K2,K1
     */
    @Test
    void testStartNextToCrossedTile() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("K2,K1");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Wenn der Zug ein Feld enthält, das bereits angekreuzt ist, dann ist der Zug nicht valide.
     * <p>
     * Äquivalenzklasse: Das erste Kreuz liegt weder in Spalte H noch grenzt es an ein bereits angekreuztes
     * Feld an. (Eingabefehler #1)
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
     * Zug: B5,C5,D5,E5
     */
    @Test
    void testInvalidFirstCross() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("B5,C5,D5,E5");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
