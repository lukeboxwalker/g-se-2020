package de.techfak.se.lwalkenhorst.domain.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientLoginController {

    @FXML
    private Button connect;

    @FXML
    private TextField address;

    @FXML
    private TextField port;

    public void onAction(final ActionHandler handler) {
        connect.setOnAction(event -> {
            try {
                final String serverAddress = address.getText();
                final int serverPort = Integer.parseInt(port.getText());
                handler.accept(serverAddress, serverPort, "lukas");
            } catch (NumberFormatException e) {
                System.out.println("Port need to be numeric");
            }
        });
    }

    public interface ActionHandler {
        void accept(String host, int port, String name);
    }
}
