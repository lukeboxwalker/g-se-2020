package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.Board;
import javafx.scene.Group;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import java.util.function.BiConsumer;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public final class View {

    public StackPane[][] createGrid(Board board, GridPane gridPane, int size, BiConsumer<Integer, Integer> consumer) {
        int lengthX = board.getLengthX();
        int lengthY = board.getLengthY();
        final StackPane[][] panes = new StackPane[lengthX][lengthY];
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                final StackPane pane = new StackPane();
                pane.setPrefHeight(size);
                pane.setPrefWidth(size);
                pane.setStyle("-fx-background-color: " + board.getTileAt(x, y).getColor().getFxName());

                if (x == board.getStartColumn()) {
                    InnerShadow shadow = new InnerShadow(5, WHITE);
                    shadow.setWidth(size / 2.0);
                    shadow.setHeight(size / 2.0);
                    pane.setEffect(shadow);
                } else {
                    pane.setEffect(new InnerShadow(5, BLACK));
                }

                int finalX = x;
                int finalY = y;
                pane.setOnMouseClicked((event) -> consumer.accept(finalX, finalY));

                panes[x][y] = pane;
                gridPane.add(pane, x, y);
                if (x == 0) {
                    RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setVgrow(Priority.ALWAYS);
                    gridPane.getRowConstraints().add(rowConstraints);
                }
                if (board.getTileAt(x, y).isCrossed()) {
                    addCrossToPane(size, pane);
                }
            }
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        return panes;
    }

    public void removeCrossFromPane(StackPane pane) {
        if (!pane.getChildren().isEmpty()) {
            pane.getChildren().remove(pane.getChildren().size() - 1);
        }
    }

    public void addCrossToPane(int size, StackPane pane) {
        Line line1 = new Line(-size / 2.5, 0, size / 2.5, 0);
        line1.setStrokeWidth(4);
        line1.setRotate(45);
        line1.endXProperty().bind(pane.heightProperty().divide(2.5));
        line1.startXProperty().bind(pane.heightProperty().divide(-2.5));

        Line line2 = new Line(0, -size / 2.5, 0, size / 2.5);
        line2.setStrokeWidth(4);
        line2.setRotate(45);
        line2.endYProperty().bind(pane.heightProperty().divide(2.5));
        line2.startYProperty().bind(pane.heightProperty().divide(-2.5));

        Group cross =  new Group(line1, line2);
        cross.setStyle("-fx-background-color: black");
        pane.getChildren().add(cross);
    }
}
