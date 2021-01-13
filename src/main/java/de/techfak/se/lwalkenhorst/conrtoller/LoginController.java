package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.client.Client;
import de.techfak.se.lwalkenhorst.exception.NoConnectionException;
import de.techfak.se.lwalkenhorst.exception.RegistrationException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.view.ChatDisplay;
import de.techfak.se.lwalkenhorst.view.TextureUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private Button connectButton;

    @FXML
    private ChatDisplay chatDisplay;

    public void initialize(final Stage stage, final Game game) {
        chatDisplay.info("Starting multiplayer");
        root.setBackground(TextureUtils.createBackgroundImage("/assets/background.png", root));
        this.address.setText("http://localhost:8080");
        this.connectButton.setOnMouseClicked(event -> connect());
    }

    private void connect() {
        if (name.getText().isEmpty()) {
            chatDisplay.warn("No name given!");
            return;
        }
        if (address.getText().isEmpty()) {
            chatDisplay.warn("No server address given!");
            return;
        }
        final Client client = new Client(address.getText());
        chatDisplay.info("Connecting to server " + address.getText() + " ...");
        Platform.runLater(() -> {
            try {
                client.connect();
                client.register(name.getText());
            } catch (NoConnectionException | RegistrationException e) {
                chatDisplay.warn(e.getMessage());
            }
        });
    }
}
