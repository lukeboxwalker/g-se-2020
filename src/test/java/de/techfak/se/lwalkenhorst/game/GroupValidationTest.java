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
 * Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist, d.h. alle angekreuzten
 * Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
 * <p>
 * Äquivalenzklassen:
 * <p>
 * Normalablauf #1:
 * Die angekreuzten Felder hängen im Sinne der Vierer-Nachbarschaft zusammen.
 * <p>
 * Normalablauf #2:
 * Ein einzelnes Kreuz ist eine valide Gruppe.
 * <p>
 * Eingabefehler #1:
 * Die angekreuzten Felder sind nicht zusammenhängend, d.h. mindestens ein Feld steht mit
 * keinem anderen Feld des Zuges in Vierer-Nachbarschaft
 * <p>
 * Eingabefehler #2:
 * Die angekreuzten Felder bilden zwei nicht zusammenhängende Untergruppen, d.h. es gibt mindestens zwei Gruppen
 * die nicht miteinander verbunden sind.
 */
class GroupValidationTest {

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
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder hängen zusammen (Normalablauf #1)
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
     * Zug: F4,E4,E5,D5
     */
    @Test
    void testValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,D5");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * <p>
     * Äquivalenzklasse: Ein einzelnes Kreuz ist eine valide Gruppe (Normalablauf #2)
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
     * Zug: H4
     */
    @Test
    void testSinglePositionValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H4");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder sind nicht zusammenhängend, d.h. mindestens ein Feld steht mit
     * keinem anderen Feld des Zuges in Vierer-Nachbarschaft (Eingabefehler #1)
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
     * Zug: F4,E4,E5,G2
     */
    @Test
    void testOnePositionNotInGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,G2");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    /**
     * Akzeptanzkriterium: Der Zug ist nur dann valide, wenn er in sich zusammenhängend ist,
     * d.h. alle angekreuzten Felder grenzen im Sinne der Vierer-Nachbarschaft aneinander an.
     * <p>
     * Äquivalenzklasse: Die angekreuzten Felder bilden zwei nicht zusammenhängende Untergruppen, d.h.
     * es gibt mindestens zwei Gruppen die nicht miteinander verbunden sind. (Eingabefehler #2)
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
     * Zug: F4,E4,E5,I5,J5,K5
     */
    @Test
    void testTwoSeparateGroups() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,I5,J5,K5");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
