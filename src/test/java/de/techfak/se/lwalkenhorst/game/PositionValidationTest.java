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

    @Test
    void testPositionsInBounds() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            Assertions.assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -22, -144, -6205, Integer.MIN_VALUE, 15, 16, 32, 64, 1001, Integer.MAX_VALUE})
    void testPositionXToLarge(final int column) {
        final Turn turn = turnFactory.createTurn(List.of(new TilePosition(1, column)));
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -22, -144, -6205, Integer.MIN_VALUE, 7, 8, 29, 82, 2587, Integer.MAX_VALUE})
    void testPositionYToLarge(final int row) {
        final Turn turn = turnFactory.createTurn(List.of(new TilePosition(row, 7)));
        Assertions.assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }
}
