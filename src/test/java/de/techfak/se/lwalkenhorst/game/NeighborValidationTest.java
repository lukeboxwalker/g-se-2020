package de.techfak.se.lwalkenhorst.game;


import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;
import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        final GameFactory factory = new GameFactory(7, 15, TurnValidator::new);
        game = factory.createGame(TEST_BOARD);
        game.play();
    }

    @Test
    void testStartInColH() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    void testStartNextToCrossedTile() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("K2,K1");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    void testInvalidFirstCross() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("B5,C5,D5,E5");
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final TilePosition position : turn.getPositionsToCross()) {
            Assertions.assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
