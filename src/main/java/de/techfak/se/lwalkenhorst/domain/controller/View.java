package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Position;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.function.BiConsumer;

import static javafx.scene.paint.Color.*;

public final class View {

    public FxBoard createGrid(final Board board, final GridPane gridPane,
                              final int size, final BiConsumer<Integer, Integer> consumer) {
        final int lengthX = board.getLengthX();
        final int lengthY = board.getLengthY();
        final StackPane[][] panes = new StackPane[lengthX][lengthY];
        final StackPane[] pointPanes = new StackPane[lengthX];
        final FxBoard fxBoard = new FxBoard(panes, pointPanes, size);
        for (int x = 0; x < lengthX; x++) {
            for (int y = 0; y < lengthY; y++) {
                final StackPane pane = new StackPane();
                pane.setPrefHeight(size);
                pane.setPrefWidth(size);
                pane.setStyle("-fx-background-color: " + board.getTileAt(x, y).getColor().getFxName());

                if (x == board.getStartColumn()) {
                    final InnerShadow shadow = new InnerShadow(5, WHITE);
                    shadow.setWidth(size / 2.0);
                    shadow.setHeight(size / 2.0);
                    pane.setEffect(shadow);
                } else {
                    pane.setEffect(new InnerShadow(5, BLACK));
                }

                final int finalX = x;
                final int finalY = y;
                pane.setOnMouseClicked((event) -> consumer.accept(finalX, finalY));

                panes[x][y] = pane;
                gridPane.add(pane, x, y);
                if (x == 0) {
                    final RowConstraints rowConstraints = new RowConstraints();
                    rowConstraints.setVgrow(Priority.ALWAYS);
                    gridPane.getRowConstraints().add(rowConstraints);
                }
                if (board.getTileAt(x, y).isCrossed()) {
                    fxBoard.addCrossToTile(new Position(x, y));
                }
            }
            final ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
        for (int x = 0; x < lengthX; x++) {
            final StackPane pane = new StackPane();
            pane.setPrefHeight(size);
            pane.setPrefWidth(size);
            final Text text = new Text(String.valueOf(board.getPointsForCol(x)));
            pane.getChildren().add(text);
            pointPanes[x] = pane;
            if (board.isColumnFull(x)) {
                fxBoard.markPointsInCol(x);
            }
            gridPane.add(pane, x, lengthY + 1);
        }
        final RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        gridPane.getRowConstraints().add(rowConstraints);
        return fxBoard;
    }
}
