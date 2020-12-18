package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.Bounds;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BoardDisplay extends GridPane {

    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    public BoardDisplay(final Board board, final ImageFactory imageFactory) {
        super();
        final TileDisplayFactory tileDisplayFactory = new TileDisplayFactory(imageFactory);
        final Bounds bounds = board.getBounds();
        for (int row = 0; row < bounds.getRows(); row++) {
            for (int column = 0; column < bounds.getColumns(); column++) {
                final TileDisplay tileDisplay = tileDisplayFactory.createTileView(row, column, board);
                tileDisplay.registerClickHandler(this::handle);
                add(tileDisplay, column, row);
            }
        }
    }

    public void handle(final TileDisplay tileDisplay) {
        clickHandlers.forEach(clickHandler -> clickHandler.handle(tileDisplay));
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }
}
