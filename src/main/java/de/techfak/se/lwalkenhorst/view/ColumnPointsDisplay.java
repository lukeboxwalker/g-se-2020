package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.RuleManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class ColumnPointsDisplay extends GridPane {

    private static final String POINT_ONE = "/assets/points/point_one.png";
    private static final String POINT_TWO = "/assets/points/point_two.png";
    private static final String POINT_THREE = "/assets/points/point_three.png";
    private static final String POINT_FIVE = "/assets/points/point_five.png";

    private static final String MARK_CIRCLE = "/assets/points/mark_circle.png";

    private final Map<Integer, PointDisplay> columnMap = new HashMap<>();

    public ColumnPointsDisplay(final Board board, final RuleManager manager) {
        super();
        final ImageView markedImage = TextureUtils.loadTexture(MARK_CIRCLE);
        for (int column = 0; column < board.getBounds().getColumns(); column++) {
            final int pointsForColumn = manager.getPointsForCol(column);
            final ImageView pointImage = getImageForColumn(pointsForColumn);
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


    private ImageView getImageForColumn(final int pointsForColumn) {
        switch (pointsForColumn) {
            case 1:
                return TextureUtils.loadTexture(POINT_ONE);
            case 2:
                return TextureUtils.loadTexture(POINT_TWO);
            case 3:
                return TextureUtils.loadTexture(POINT_THREE);
            case 5:
                return TextureUtils.loadTexture(POINT_FIVE);
            default:
                throw new IllegalArgumentException();
        }
    }
}
