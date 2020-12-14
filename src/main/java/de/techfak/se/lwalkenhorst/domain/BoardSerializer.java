package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

public class BoardSerializer {

    private static final String BREAK = "\n";
    private static final int BOARD_SIZE_X = 15;
    private static final int BOARD_SIZE_Y = 7;

    private final Map<Character, Color> colorMap;

    public BoardSerializer() {
        this.colorMap = new HashMap<>();
        Arrays.stream(Color.values()).forEach(color -> colorMap.put(color.getIdentifier(), color));
    }

    public String serialize(final Board board) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < board.getLengthY(); y++) {
            for (int x = 0; x < board.getLengthX(); x++) {
                stringBuilder.append(board.getTileAt(x, y).getColor().getIdentifier());
            }
            stringBuilder.append(BREAK);
        }
        return stringBuilder.toString();
    }

    public Board deSerialize(final File file) throws BoardCreationException, IOException {
        return deSerialize(Files.newInputStream(Paths.get(file.getPath())));
    }

    private void createBoardLine(final String line, final int col, final Tile[][] tiles) throws BoardCreationException {
        char colorIdentifier;
        for (int x = 0; x < line.length(); x++) {
            colorIdentifier = line.charAt(x);
            if (colorMap.containsKey(Character.toLowerCase(colorIdentifier))) {
                tiles[x][col] = new Tile(colorMap.get(Character.toLowerCase(colorIdentifier)));
                if (Character.isUpperCase(colorIdentifier)) {
                    tiles[x][col].cross();
                }
            } else {
                throw new BoardCreationException("Unknown color: " + colorIdentifier + " !");
            }
        }
    }

    public Board deSerialize(final String string) throws BoardCreationException {
        return deSerialize(new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8)));
    }

    public Board deSerialize(final InputStream inputStream) throws BoardCreationException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            final Tile[][] tiles = new Tile[BOARD_SIZE_X][BOARD_SIZE_Y];
            String line;
            for (int y = 0; y < BOARD_SIZE_Y; y++) {
                line = reader.readLine();
                if (line == null) {
                    throw new BoardCreationException("Wrong board size y value!");
                } else {
                    line = line.replace(BREAK, "");
                    if (line.length() != BOARD_SIZE_X) {
                        throw new BoardCreationException("Wrong board size x value!");
                    }
                    createBoardLine(line, y, tiles);
                }
            }
            if (reader.readLine() != null) {
                throw new BoardCreationException("Wrong board size y value!");
            }
            return new Board(tiles);
        } catch (IOException e) {
            throw new BoardCreationException(e);
        }
    }
}
