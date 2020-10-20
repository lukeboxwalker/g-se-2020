package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.MultiplayerGame;
import de.techfak.se.lwalkenhorst.domain.controller.ClientLoginController;
import de.techfak.se.lwalkenhorst.domain.controller.GameController;
import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;
import de.techfak.se.lwalkenhorst.domain.exception.NoConnectionException;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ClientApplication extends Application {

    private static MultiplayerGame game;

    @Override
    public void start(final Stage stage) throws IOException {
        //Loading view
        FXMLLoader fxmlLoader = load(Thread.currentThread().getContextClassLoader().getResource("view/GameView.fxml"));
        final Pane root = fxmlLoader.load();
        final GameController gameController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        stage.setTitle("GSE Nochmal!");
        stage.setScene(scene);
        stage.show();

        fxmlLoader = load(getClass().getClassLoader().getResource("view/ClientLogin.fxml"));
        Pane loginRoot = fxmlLoader.load();
        ClientLoginController loginController = fxmlLoader.getController();

        final Stage connectionWindow = new Stage();
        connectionWindow.initModality(Modality.APPLICATION_MODAL);
        connectionWindow.setTitle("Connect to server");

        final BoardSerializer serializer = new BoardSerializer();
        loginController.onAction((host, port, username) -> {
            try {
                HTTPClient client = new HTTPClient(host, port);
                ParticipateResponse response = client.participateRequest(username);
                if (response.isSuccess()) {
                    Board board = serializer.deSerialize(response.getBoard());
                    game.init(board, client);
                    gameController.init(game);
                    game.start();
                } else {
                    System.out.println("Cannot play on server. Is the server full?");
                }
                connectionWindow.close();
            } catch (NoConnectionException | BoardCreationException e) {
                e.printStackTrace();
            }

        });
        Scene scene2 = new Scene(loginRoot);
        connectionWindow.setScene(scene2);
        connectionWindow.showAndWait();
    }

    public static void start(MultiplayerGame multiplayerGame) {
        game = multiplayerGame;
        main();
    }

    public static void main(String... args) {
        launch(args);
    }

    private FXMLLoader load(URL url) {
        return new FXMLLoader(url);
    }
}
