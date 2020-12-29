package de.techfak.se.lwalkenhorst.multiplayer.game;

import de.techfak.se.lwalkenhorst.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.multiplayer.game.exceptions.InvalidFieldException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The interface to parse a board based on a file or a string.
 */
public interface BoardParser {

    Board parse(final List<String> lines) throws InvalidBoardLayoutException, InvalidFieldException;

    Board parse(final File boardFile) throws InvalidBoardLayoutException, InvalidFieldException, IOException;
}
