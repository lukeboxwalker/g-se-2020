package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.cli.CliGame;
import de.techfak.se.lwalkenhorst.exception.InvalidBoardException;
import de.techfak.se.lwalkenhorst.exception.InvalidParameterException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.GameFactory;
import de.techfak.se.lwalkenhorst.game.TurnValidator;

import java.util.Arrays;

/**
 * The main class, contains just the main method to start the application.
 */

public final class Encore {
    private static final int EXIT_WRONG_FILE = 100;
    private static final int EXIT_INVALID_BOARD = 101;
    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    private Encore() {
    }

    public static void main(final String... args) {
        System.exit(Encore.run(args));
    }

    private static int run(final String... args) {
        try {
            final GameFactory factory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
            final Game game = new GameInitializer(factory, Arrays.asList(args)).initGame();
            final CliGame cliGame = new CliGame();
            cliGame.play(game);
        } catch (InvalidBoardException e) {
            return EXIT_INVALID_BOARD;
        } catch (InvalidParameterException e) {
            return EXIT_WRONG_FILE;
        }
        return 0;
    }
}
