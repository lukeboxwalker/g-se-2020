package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.MultiplayerGame;
import de.techfak.se.lwalkenhorst.domain.argumentparser.ArgumentParser;
import de.techfak.se.lwalkenhorst.domain.argumentparser.CommandLine;
import de.techfak.se.lwalkenhorst.domain.argumentparser.CommandLineOption;
import de.techfak.se.lwalkenhorst.domain.argumentparser.Option;
import de.techfak.se.lwalkenhorst.domain.argumentparser.ParseException;
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
    private static final String GUI = "gui";
    private static final String CLIENT = "client";

    private GseAgain() {
    }

    private void start(final String... args) {
        try {
            final CommandLine commandLine = new ArgumentParser().parse(createOptions(), args);
            final Game game;
            final BoardSerializer boardSerializer = new BoardSerializer();
            if (commandLine.hasArgument()) {
                game = new Game(boardSerializer.deSerialize(new File(commandLine.getArgument())));
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
            if (commandLine.hasOption(GUI)) {
                GseApplication.start(game);
            } else if (commandLine.hasOption(CLIENT)) {
                try (MultiplayerGame multiplayerGame = new MultiplayerGame()) {
                    ClientApplication.start(multiplayerGame);
                }

//                HTTPClient client = new HTTPClient("localhost", 8088);
//                ParticipateResponse response = client.participateRequest(args.length == 1 ? args[0] : "lukas");
//                if (response.isSuccess()) {
//                    Board board = boardSerializer.deSerialize(response.getBoard());
//                    try (MultiplayerGame multiplayerGame = new MultiplayerGame(board, client)) {
//                        Application.start(multiplayerGame);
//                    }
//                } else {
//                    System.out.println("Cannot play on server. Is the server full?");
//                }
            } else {
                final Terminal terminal = new Terminal(game);
                terminal.listenForInstructions();
            }
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private List<Option> createOptions() {
        Option guiOption = CommandLineOption.builder().withName(GUI).conflictsOptions(CLIENT).build();
        Option clientOption = CommandLineOption.builder().withName(CLIENT).conflictsOptions(GUI).build();
        return Arrays.asList(guiOption, clientOption);
    }

    public static void main(final String... args) {
        final GseAgain gseAgain = new GseAgain();
        gseAgain.start(args);
    }

}
