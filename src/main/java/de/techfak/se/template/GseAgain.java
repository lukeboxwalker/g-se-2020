package de.techfak.se.template;

import de.techfak.se.template.domain.BoardCreationException;
import de.techfak.se.template.domain.BoardFactory;
import de.techfak.se.template.domain.ExitCodeException;
import de.techfak.se.template.domain.Game;
import de.techfak.se.template.domain.Terminal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
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
                game = loadGame(new URI(args[0]));
            } else {
                final URL defaultURL = (getClass().getClassLoader().getResource(DEFAULT_BOARD_CONFIG));
                if (defaultURL != null) {
                    game = loadGame(defaultURL.toURI());
                } else {
                    throw new BoardCreationException("File " + DEFAULT_BOARD_CONFIG + "did not exist!");
                }
            }
            Terminal terminal = new Terminal(game);
            terminal.listenForInstructions();
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage());
        } catch (ExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }

    private Game loadGame(final URI fileURI) throws BoardCreationException {
        final Game game;
        try {
            final BoardFactory factory = new BoardFactory(fileURI);
            game = new Game(factory.createBoard());
        } catch (IOException e) {
            throw new BoardCreationException(e);
        }
        return game;
    }

    public static void main(final String... args) {
        final GseAgain gseAgain = new GseAgain();
        gseAgain.start(args);
    }

}
