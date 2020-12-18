package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.RuleManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class ColumnPointsDisplay extends GridPane {

    private final Map<Integer, PointDisplay> columnMap = new HashMap<>();

    public ColumnPointsDisplay(final Board board, final RuleManager manager, final ImageFactory imageFactory) {
        super();
        for (int column = 0; column < board.getBounds().getColumns(); column++) {
            final int pointsForColumn = manager.getPointsForCol(column);
            final ImageView pointImage = getImageForColumn(pointsForColumn, imageFactory);
            final ImageView markedImage = imageFactory.createCircleImage();
            final PointDisplay display = new PointDisplay(pointImage, markedImage);
            columnMap.put(column, display);
            add(display, column, 0);
        }
    }

    public void mark(final int column) {
        if (columnMap.containsKey(column)) {
            columnMap.get(column).mark();
        } else {
            throw new IllegalArgumentException();
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
