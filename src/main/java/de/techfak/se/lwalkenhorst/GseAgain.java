package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.Game;
import de.techfak.se.lwalkenhorst.domain.cli.Terminal;
import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;


/**
 * The main class, contains just the main method to start the application.
 */
@CommandLine.Command(exitCodeOnInvalidInput = 100)
public final class GseAgain implements Callable<Integer> {

    private static final String DEFAULT_BOARD_CONFIG = "default.txt";

    @CommandLine.Option(names = {"-f", "--file"}, description = "Board")
    private File file;

    private GseAgain() {
    }


    public static void main(final String... args) {
        final CommandLine commandLine = new CommandLine(new GseAgain());
        final int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            final Game game;
            final BoardSerializer boardSerializer = new BoardSerializer();
            if (file == null) {
                return 100;
            } else {
                game = new Game(boardSerializer.deSerialize(file));
            }
            final Terminal terminal = new Terminal(game);
            terminal.listenForInstructions();
        } catch (BoardCreationException e) {
            return 101;
        } catch (IOException e) {
            return 100;
        }
        return 0;
    }
}
