package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.AbstractBoard;
import de.techfak.se.lwalkenhorst.domain.Position;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;

public class FxBoard extends AbstractBoard<StackPane> {

    private static final int CIRCLE_STROKE = 3;
    private static final int CIRCLE_RADIUS = 4;
    private static final int ROTATION = 45;

    private static final int LINE_STROKE = 4;
    private static final double SIZE = 2.5;


    private final int tileSize;
    private final StackPane[] pointTiles;

    public FxBoard(final StackPane[][] tiles, final StackPane[] pointTiles, final int tileSize) {
        super(tiles);
        this.pointTiles = pointTiles;
        this.tileSize = tileSize;
    }

    public void removeCrossFromTile(final Position position) {
        if (inBounds(position)) {
            final StackPane pane = getTileAt(position);
            if (!pane.getChildren().isEmpty()) {
                pane.getChildren().remove(pane.getChildren().size() - 1);
            }
        }
    }

    public void markPointsInCol(final int col) {
        if (inBounds(col, 0)) {
            final StackPane pane = pointTiles[col];
            if (pane.getChildren().size() > 1) {
                return;
            }
            final Circle circle = new Circle();
            circle.setStroke(BLACK);
            circle.setStrokeWidth(CIRCLE_STROKE);
            circle.setFill(TRANSPARENT);
            circle.radiusProperty().bind(pane.heightProperty().divide(CIRCLE_RADIUS));
            pane.getChildren().add(circle);
        }
    }

    public void addCrossToTile(final Position position) {
        final StackPane pane = getTileAt(position);
        pane.getChildren().add(new Group(createLine(SIZE, true, pane), createLine(SIZE, false, pane)));
    }

    public Line createLine(final double size, final boolean horizontal, final StackPane pane) {
        final Line line;
        if (horizontal) {
            line = new Line(-tileSize / size, 0, tileSize / size, 0);
            line.endXProperty().bind(pane.heightProperty().divide(size));
            line.startXProperty().bind(pane.heightProperty().divide(-size));
        } else {
            line = new Line(0, -tileSize / size, 0, tileSize / size);
            line.endYProperty().bind(pane.heightProperty().divide(size));
            line.startYProperty().bind(pane.heightProperty().divide(-size));
        }
        line.setStrokeWidth(LINE_STROKE);
        line.setRotate(ROTATION);
        line.setFill(BLACK);
        return line;
    }
}
