package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Points;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static javafx.scene.paint.Color.WHITE;

public class GameOverScreen extends VBox {

    public GameOverScreen(final Points points, final AnchorPane root) {
        super();
        final Text text = new Text("Game over. Your score: " + points);
        text.setFill(WHITE);
        text.setFont(Font.font("arial", FontWeight.BOLD, 40));
        getChildren().add(text);

        prefHeightProperty().bind(root.prefHeightProperty());
        prefWidthProperty().bind(root.prefWidthProperty());
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        setAlignment(Pos.CENTER);
    }
}
