package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends GridPane {

    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    public BoardView(final Board board, final ImageFactory imageFactory) {
        super();
        final TileViewFactory tileViewFactory = new TileViewFactory(imageFactory);
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 15; column++) {
                final TileView tileView = tileViewFactory.createTileView(row, column, board);
                tileView.registerClickHandler(this::handle);
                add(tileView, column, row);
            }
        }
    }

    public void handle(final TileView tileView) {
        clickHandlers.forEach(clickHandler -> clickHandler.handle(tileView));
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }
}
