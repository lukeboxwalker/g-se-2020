package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class BoardParserImplTest {

    private BoardParser boardParser;

    @BeforeEach
    void setUp() {
        boardParser = new BoardParserImpl();
    }

    @Test
    void testValidateCorrectBoard() throws Exception {
        final File file = new File(getClass().getResource("/boards/boardNormal.txt").toURI());
        Assertions.assertNotNull(boardParser.parse(file));
    }

    @Test
    void testBoardXToShort() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards/boardXtoShort.txt").toURI());
        Assertions.assertThrows(InvalidBoardLayoutException.class, () -> boardParser.parse(file));
    }

    @Test
    void testBoardXToLarge() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards/boardXtoLarge.txt").toURI());
        Assertions.assertThrows(InvalidBoardLayoutException.class, () -> boardParser.parse(file));
    }

    @Test
    void testBoardYToShort() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards/boardYtoShort.txt").toURI());
        Assertions.assertThrows(InvalidBoardLayoutException.class, () -> boardParser.parse(file));
    }

    @Test
    void testBoardYToLarge() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards/boardYtoLarge.txt").toURI());
        Assertions.assertThrows(InvalidBoardLayoutException.class, () -> boardParser.parse(file));
    }

    @Test
    void testWrongColorChar() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards/boardWrongChar.txt").toURI());
        Assertions.assertThrows(InvalidFieldException.class, () -> boardParser.parse(file));
    }

    @Test
    void testValidateCrossedBoard() throws Exception {
        final File file = new File(getClass().getResource("/boards/boardAlreadyCrossed.txt").toURI());
        Assertions.assertNotNull(boardParser.parse(file));
    }

    @Test
    void testFolderAsInput() throws URISyntaxException {
        final File file = new File(getClass().getResource("/boards").toURI());
        Assertions.assertThrows(IOException.class, () -> boardParser.parse(file));
    }
}
