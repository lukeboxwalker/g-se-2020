package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.cli.CliGame;
import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.GameFactory;

import java.io.File;
import java.io.IOException;

/**
 * The main class, contains just the main method to start the application.
 */

public final class Encore {

    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    private Encore() {
    }

    public static void main(final String... args) {
        System.exit(Encore.run(args));
    }

    private static int run(final String... args) {
        if (args.length != 2 || !args[0].equals("-f")) {
            return 100;
        }
        final File file = new File(args[1]);
        if (!file.exists() || file.isDirectory()) {
            return 100;
        }
        try {
            final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS);
            final Game game = gameFactory.createGame(file);
            final CliGame cliGame = new CliGame();
            cliGame.play(game);
        } catch (InvalidFieldException | InvalidBoardLayoutException e) {
            e.printStackTrace();
            return 101;
        } catch (IOException e) {
            return 100;
        }
        return 0;
    }

}
