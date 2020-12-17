package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import javafx.scene.layout.GridPane;

public class BoardView extends GridPane {

    public BoardView(final Board board) {
        super();
        final ImageFactory imageFactory = new ImageFactory();
        final TileViewFactory tileViewFactory = new TileViewFactory(imageFactory);
        for (int row = 0; row < 7; row++) {
            for (int column = 0; column < 15; column++) {
                final TileView tileView = tileViewFactory.createTileView(row, column, board.getTileAt(row, column));
                add(tileView, column, row);
            }
        }
    }
}
