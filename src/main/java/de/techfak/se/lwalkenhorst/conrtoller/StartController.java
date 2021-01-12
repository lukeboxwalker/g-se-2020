package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.view.TextureUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;


public class StartController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button singlePlayer;

    @FXML
    private Button multiPlayer;

    public void initialize(final Stage stage, final Game game) {
        root.setBackground(TextureUtils.createBackgroundImage("/assets/background.png", root));
        this.singlePlayer.setText("Singleplayer");
        this.singlePlayer.setOnMouseClicked(event -> startSinglePlayer(stage, game));
        this.multiPlayer.setText("Multiplayer");
        this.multiPlayer.setOnMouseClicked(event -> startMultiPlayer(stage, game));
    }

    private void startMultiPlayer(final Stage stage, final Game game) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        final Pane root;
        try {
            root = fxmlLoader.load();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        final LoginController controller = fxmlLoader.getController();
        controller.initialize(stage, game);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void startSinglePlayer(final Stage stage, final Game game) {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameView.fxml"));
        final Pane root;
        try {
            root = fxmlLoader.load();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        final GameController controller = fxmlLoader.getController();
        controller.initialize(game);
        game.play();
        final Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
