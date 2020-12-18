package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidBoardLayoutException;
import de.techfak.se.lwalkenhorst.exception.InvalidFieldException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameFactory {

    private final Map<Character, TileColor> colorMap = new HashMap<>();
    private final Bounds bounds;
    private final TurnValidatorFactory turnValidatorFactory;

    public GameFactory(final int rows, final int columns, final TurnValidatorFactory turnValidatorFactory) {
        this.turnValidatorFactory = turnValidatorFactory;
        this.bounds = new Bounds(rows, columns);
        this.colorMap.put('r', TileColor.RED);
        this.colorMap.put('b', TileColor.BLUE);
        this.colorMap.put('g', TileColor.GREEN);
        this.colorMap.put('o', TileColor.ORANGE);
        this.colorMap.put('y', TileColor.YELLOW);
    }

    public Game createGame(final File file) throws InvalidBoardLayoutException, InvalidFieldException, IOException {
        final BoardImpl board = parse(Files.readAllLines(Paths.get(file.getPath())));
        return new GameImpl(board, turnValidatorFactory.create(board));
    }

    public Game createGame(final List<String> lines) throws InvalidBoardLayoutException, InvalidFieldException {
        final BoardImpl board = parse(lines);
        return new GameImpl(board, turnValidatorFactory.create(board));
    }

    public BoardImpl parse(final List<String> lines) throws InvalidBoardLayoutException, InvalidFieldException {
        if (lines.size() != bounds.getRows()) {
            throw new InvalidBoardLayoutException("Wrong board row value!");
        }
        final TileImpl[][] tiles = new TileImpl[bounds.getRows()][bounds.getColumns()];
        for (int row = 0; row < bounds.getRows(); row++) {
            tiles[row] = createBoardRow(lines.get(row));
        }
        return new BoardImpl(tiles, bounds);
    }

    private TileImpl[] createBoardRow(final String line) throws InvalidBoardLayoutException, InvalidFieldException {
        if (line.length() != bounds.getColumns()) {
            throw new InvalidBoardLayoutException("Wrong board column value!");
        }
        final TileImpl[] tiles = new TileImpl[bounds.getColumns()];
        for (int column = 0; column < line.length(); column++) {
            final char tile = line.charAt(column);
            if (colorMap.containsKey(Character.toLowerCase(tile))) {
                tiles[column] = new TileImpl(colorMap.get(Character.toLowerCase(tile)));
                if (Character.isUpperCase(tile)) {
                    tiles[column].cross();
                }
            } else {
                throw new InvalidFieldException("Unknown color: " + tile + " !");
            }
        }
        return tiles;
    }
}
