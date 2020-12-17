package de.techfak.se.lwalkenhorst.Conrtoller;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.view.BoardView;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


public class ViewController {

    @FXML
    private VBox rootBox;

    public void initialize(final Board board) {
        rootBox.getChildren().add(new BoardView(board));
    }
}
