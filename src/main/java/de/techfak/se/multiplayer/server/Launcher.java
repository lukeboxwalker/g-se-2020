package de.techfak.se.multiplayer.server;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.BoardParser;
import de.techfak.se.multiplayer.game.BoardParserImpl;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Server Launcher.
 */
@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "server 1.0",
    description = "Allows to play Encore.", exitCodeOnInvalidInput = 100)
public class Launcher implements Callable<Integer> {

    private static final int INVALID_FILE_EXIT_CODE = 100;
    private static final int INVALID_BOARD_EXIT_CODE = 101;
    private static final int DEFAULT_PORT = 8080;

    @CommandLine.Option(names = {"-f", "--file"}, description = "Die einzulesende Spielfeld-Datei.")
    private File file;

    @CommandLine.Option(names = {"-p", "--port"}, description = "Server-Port")
    private int port = DEFAULT_PORT;

    public static void main(final String[] args) {
        final int exitCode = new CommandLine(new Launcher()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        if (file == null) {
            return INVALID_FILE_EXIT_CODE;
        }
        try {
            final BoardParser boardParser = new BoardParserImpl();
            final BaseGame baseGame = new BaseGameImpl(boardParser.parse(file));
            final SynchronizedGame game = new SynchronizedGame(baseGame);
            final Server server = new Server(game);
            server.start(port);

            Thread.currentThread().join();
        } catch (InvalidBoardLayoutException | InvalidFieldException e) {
            return INVALID_BOARD_EXIT_CODE;
        } catch (IOException e) {
            return INVALID_FILE_EXIT_CODE;
        } catch (InterruptedException e) {
            return -1;
        }
        return 0;
    }
}
