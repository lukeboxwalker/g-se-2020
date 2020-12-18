package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.exception.InvalidBoardException;
import de.techfak.se.lwalkenhorst.exception.InvalidFileException;
import de.techfak.se.lwalkenhorst.exception.InvalidParameterException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.GameFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Launcher {

    private final GameFactory gameFactory;
    private final List<String> args;

    public Launcher(final GameFactory gameFactory, final List<String> args) {
        this.gameFactory = gameFactory;
        this.args = args;
    }

    public Game initGame() throws InvalidParameterException, InvalidBoardException {
        if (args.size() != 2 || !args.get(0).equals("-f")) {
            throw new InvalidParameterException("Parameter invalid");
        }
        final File file = new File(args.get(1));
        if (!file.exists() || file.isDirectory()) {
            throw new InvalidFileException("Invalid file");
        }
        try {
            return gameFactory.createGame(file);
        } catch (IOException ioe) {
            throw new InvalidFileException(ioe);
        }
    }
}
