package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.cli.Terminal;
import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;
import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;
import de.techfak.se.lwalkenhorst.domain.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * The main class, contains just the main method to start the application.
 */
public final class GseAgain {

    private static final String DEFAULT_BOARD_CONFIG = "default.txt";
    private static final String GUI = "--gui";
    private static final String GUI_ALIAS = "-g";

    private boolean startGui = false;


    private GseAgain() {
    }

    private void start(final String... args) {
        try {
            final Game game;
            final BoardSerializer boardSerializer = new BoardSerializer();
            final List<String> argsList = Arrays.asList(args);
            if (argsList.contains(GUI) || argsList.contains(GUI_ALIAS)) {
                argsList.remove(GUI);
                argsList.remove(GUI_ALIAS);
                startGui = true;
            }
            if (argsList.size() == 1) {
                game = new Game(boardSerializer.deSerialize(new File(argsList.get(0))));
            } else {
                try (InputStream inputStream = Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(DEFAULT_BOARD_CONFIG)) {
                    if (inputStream == null) {
                        throw new BoardCreationException("File " + DEFAULT_BOARD_CONFIG + " did not exist!");
                    } else {
                        game = new Game(boardSerializer.deSerialize(inputStream));
                    }
                } catch (IOException e) {
                    throw new BoardCreationException(e);
                }
            }
            if (startGui) {
                GseApplication.start(game);
            } else {
                final Terminal terminal = new Terminal(game);
                terminal.listenForInstructions();
            }
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }

    public static void main(final String... args) {
        final GseAgain gseAgain = new GseAgain();
        gseAgain.start(args);
    }

}
