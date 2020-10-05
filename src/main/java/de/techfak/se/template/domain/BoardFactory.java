package de.techfak.se.template.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    private static final int BOARD_SIZE_X = 15;
    private static final int BOARD_SIZE_Y = 7;

    private final URI uri;
    private final Map<Character, Color> colorMap;

    public BoardFactory(final URI uri) {
        this.uri = uri;
        this.colorMap = new HashMap<>();
        Arrays.stream(Color.values()).forEach(color -> colorMap.put(color.getIdentifier(), color));
    }

    public Board createBoard() throws IOException, BoardCreationException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(uri)));
        final Tile[][] tiles = new Tile[BOARD_SIZE_X][BOARD_SIZE_Y];
        String line;
        char colorIdentifier;
        for (int y = 0; y < BOARD_SIZE_Y; y++) {
            if ((line = bufferedReader.readLine()) != null) {
                line = line.replace("\n", "");
                if (line.length() != BOARD_SIZE_X) {
                    throw new BoardCreationException("Wrong board size x value!");
                }
                for (int x = 0; x < line.length(); x++) {
                    colorIdentifier = line.charAt(x);
                    if (colorMap.containsKey(colorIdentifier)) {
                        tiles[x][y] = new Tile(colorMap.get(colorIdentifier));
                    } else {
                        throw new BoardCreationException("Unknown color: " + colorIdentifier + " !");
                    }
                }
            } else {
                throw new BoardCreationException("Wrong board size y value!");
            }
        }
        return new Board(tiles);
    }
}
