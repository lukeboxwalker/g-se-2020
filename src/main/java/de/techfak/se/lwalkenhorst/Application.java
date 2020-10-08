package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.Game;
import de.techfak.se.lwalkenhorst.domain.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Game gameModel;

    @Override
    public void start(final Stage stage) throws IOException {
        //Loading view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/GameView.fxml"));
        Pane root = fxmlLoader.load();

        //Init game controller and game
        GameController controller = fxmlLoader.getController();
        controller.init(gameModel);

        gameModel.start();

        Scene scene = new Scene(root);
        stage.setTitle("GSE Nochmal!");
        stage.setScene(scene);
        stage.show();


    }

    public static void start(Game game) {
        gameModel = game;
        main();
    }

    public static void main(String... args) {
        launch(args);
    }
}
