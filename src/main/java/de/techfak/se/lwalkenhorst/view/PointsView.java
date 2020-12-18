package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.RuleManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PointsView extends GridPane {

    public PointsView(final Board board, final RuleManager manager, final ImageFactory imageFactory) {
        super();
        for (int column = 0; column < board.getBounds().getColumns(); column++) {
            final int pointsForColumn = manager.getPointsForCol(column);
            final ImageView pointImage = getImageForColumn(pointsForColumn, imageFactory);
            add(pointImage, column, 0);
        }
    }

    private ImageView getImageForColumn(final int pointsForColumn, final ImageFactory imageFactory) {
        switch (pointsForColumn) {
            case 1:
                return imageFactory.createPointOneImage();
            case 2:
                return imageFactory.createPointTwoImage();
            case 3:
                return imageFactory.createPointThreeImage();
            case 5:
                return imageFactory.createPointFiveImage();
            default:
                throw new IllegalArgumentException();
        }
    }
}
