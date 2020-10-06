package de.techfak.se.template;

import de.techfak.se.template.domain.BoardCreationExceptionAbstract;
import de.techfak.se.template.domain.BoardFactory;
import de.techfak.se.template.domain.AbstractExitCodeException;
import de.techfak.se.template.domain.Game;
import de.techfak.se.template.domain.Terminal;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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
            if (args.length == 1) {
                game = loadGame(new File(args[0]));
            } else {
                final URL defaultURL = Thread.currentThread().getContextClassLoader().getResource(DEFAULT_BOARD_CONFIG);
                if (defaultURL == null) {
                    throw new BoardCreationExceptionAbstract("File " + DEFAULT_BOARD_CONFIG + "did not exist!");
                } else {
                    try {
                        game = loadGame(new File(defaultURL.toURI()));
                    } catch (URISyntaxException e) {
                        throw new BoardCreationExceptionAbstract(e);
                    }
                }
            }
            final Terminal terminal = new Terminal(game);
            terminal.listenForInstructions();
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }

    private Game loadGame(final File file) throws BoardCreationExceptionAbstract {
        final Game game;
        try {
            final BoardFactory factory = new BoardFactory(file);
            game = new Game(factory.createBoard());
        } catch (IOException e) {
            throw new BoardCreationExceptionAbstract(e);
        }
        return game;
    }

    public static void main(final String... args) {
        final GseAgain gseAgain = new GseAgain();
        gseAgain.start(args);
    }

}
