package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;
import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void testValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,D5");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    void testSinglePositionValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H4");
        game.applyTurn(turn);
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    void testOnePositionNotInGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,G2");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    void testTwoSeparateGroups() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,I5,J5,K5");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final CellPosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
