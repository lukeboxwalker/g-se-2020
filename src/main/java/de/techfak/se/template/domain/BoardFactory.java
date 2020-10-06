package de.techfak.se.template.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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

    private final File file;
    private final Map<Character, Color> colorMap;

    public BoardFactory(final File file) {
        this.file = file;
        this.colorMap = new HashMap<>();
        Arrays.stream(Color.values()).forEach(color -> colorMap.put(color.getIdentifier(), color));
    }

    public Board createBoard() throws IOException, BoardCreationExceptionAbstract {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath()), StandardCharsets.UTF_8)) {
            final Tile[][] tiles = new Tile[BOARD_SIZE_X][BOARD_SIZE_Y];
            String line;
            char colorIdentifier;
            for (int y = 0; y < BOARD_SIZE_Y; y++) {
                line = reader.readLine();
                if (line == null) {
                    throw new BoardCreationExceptionAbstract("Wrong board size y value!");
                } else {
                    line = line.replace("\n", "");
                    if (line.length() != BOARD_SIZE_X) {
                        throw new BoardCreationExceptionAbstract("Wrong board size x value!");
                    }
                    for (int x = 0; x < line.length(); x++) {
                        colorIdentifier = line.charAt(x);
                        if (colorMap.containsKey(colorIdentifier)) {
                            tiles[x][y] = new Tile(colorMap.get(colorIdentifier));
                        } else if (colorIdentifier == CROSS) {
                            final Tile tile = new Tile(Color.GREEN);
                            tile.cross();
                            tiles[x][y] = tile;
                        } else {
                            throw new BoardCreationExceptionAbstract("Unknown color: " + colorIdentifier + " !");
                        }
                    }
                }
            }
            return new Board(tiles);
        }
    }
}
