package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;
import de.techfak.se.lwalkenhorst.domain.BoardFactory;
import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;
import de.techfak.se.lwalkenhorst.domain.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The main class, contains just the main method to start the application.
 */
public final class GseAgain {

    private static final String DEFAULT_BOARD_CONFIG = "default.txt";

    private GseAgain() {
    }

    private void start(final String... args) {
        try {
            final Game game;
            final BoardFactory boardFactory = new BoardFactory();
            if (args.length == 1) {
                game = loadGame(boardFactory.createBoard(new File(args[0])));
            } else {
                try (InputStream inputStream = Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(DEFAULT_BOARD_CONFIG)) {
                    if (inputStream == null) {
                        throw new BoardCreationException("File " + DEFAULT_BOARD_CONFIG + " did not exist!");
                    } else {
                        game = loadGame(boardFactory.createBoard(inputStream));
                    }
                } catch (IOException e) {
                    throw new BoardCreationException(e);
                }
            }
          Application.start(game);
//            final Terminal terminal = new Terminal(game);
//            terminal.listenForInstructions();
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }

    private Game loadGame(final Board board) {
        return new Game(board);
    }

    public static void main(final String... args) {
        final GseAgain gseAgain = new GseAgain();
        gseAgain.start(args);
    }

}
