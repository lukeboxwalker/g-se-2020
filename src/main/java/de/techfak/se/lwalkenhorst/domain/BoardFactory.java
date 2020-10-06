package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private static final char CROSS = 'X';
    private static final int BOARD_SIZE_X = 15;
    private static final int BOARD_SIZE_Y = 7;

    private final Map<Character, Color> colorMap;

    public BoardFactory() {
        this.colorMap = new HashMap<>();
        Arrays.stream(Color.values()).forEach(color -> colorMap.put(color.getIdentifier(), color));
    }

    public Board createBoard(final File file) throws BoardCreationException {
        try {
            return createBoard(Files.newInputStream(Paths.get(file.getPath())));
        } catch (IOException e) {
            throw new BoardCreationException(e);
        }
    }

    private void createBoardLine(final String line, final int col, final Tile[][] tiles) throws BoardCreationException {
        char colorIdentifier;
        for (int x = 0; x < line.length(); x++) {
            colorIdentifier = line.charAt(x);
            if (colorMap.containsKey(colorIdentifier)) {
                tiles[x][col] = new Tile(colorMap.get(colorIdentifier));
            } else if (colorIdentifier == CROSS) {
                final Tile tile = new Tile(Color.GREEN);
                tile.cross();
                tiles[x][col] = tile;
            } else {
                throw new BoardCreationException("Unknown color: " + colorIdentifier + " !");
            }
        }
    }

    public Board createBoard(final InputStream inputStream) throws BoardCreationException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            final Tile[][] tiles = new Tile[BOARD_SIZE_X][BOARD_SIZE_Y];
            String line;
            for (int y = 0; y < BOARD_SIZE_Y; y++) {
                line = reader.readLine();
                if (line == null) {
                    throw new BoardCreationException("Wrong board size y value!");
                } else {
                    line = line.replace("\n", "");
                    if (line.length() != BOARD_SIZE_X) {
                        throw new BoardCreationException("Wrong board size x value!");
                    }
                    createBoardLine(line, y, tiles);
                }
            }
            return new Board(tiles);
        } catch (IOException e) {
            throw new BoardCreationException(e);
        }
    }
}
