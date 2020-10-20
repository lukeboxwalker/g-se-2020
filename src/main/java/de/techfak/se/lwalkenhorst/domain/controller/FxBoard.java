package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.AbstractBoard;
import de.techfak.se.lwalkenhorst.domain.Position;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;

public class FxBoard extends AbstractBoard<StackPane> {

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
            circle.setStrokeWidth(3);
            circle.setFill(TRANSPARENT);
            circle.radiusProperty().bind(pane.heightProperty().divide(4));
            pane.getChildren().add(circle);
        }
    }

    public void addCrossToTile(final Position position) {
        final StackPane pane = getTileAt(position);
        pane.getChildren().add(new Group(createLine(2.5, true, pane), createLine(2.5, false, pane)));
    }

    public Line createLine(final double size, final boolean horizontal, final StackPane pane) {
        final Line line;
        if (horizontal) {
            line = new Line(-tileSize / size, 0, tileSize / size, 0);
            line.endXProperty().bind(pane.heightProperty().divide(2.5));
            line.startXProperty().bind(pane.heightProperty().divide(-2.5));
        } else {
            line = new Line(0, -tileSize / size, 0, tileSize / size);
            line.endYProperty().bind(pane.heightProperty().divide(2.5));
            line.startYProperty().bind(pane.heightProperty().divide(-2.5));
        }
        line.setStrokeWidth(4);
        line.setRotate(45);
        line.setFill(BLACK);
        return line;
    }
}
