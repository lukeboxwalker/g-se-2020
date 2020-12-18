package de.techfak.se.lwalkenhorst;


import de.techfak.se.lwalkenhorst.conrtoller.GuiController;
import de.techfak.se.lwalkenhorst.exception.InvalidBoardException;
import de.techfak.se.lwalkenhorst.exception.InvalidParameterException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.GameFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuiApplication extends Application {

    private static final int EXIT_WRONG_FILE = 100;
    private static final int EXIT_INVALID_BOARD = 101;
    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    private Game game;

    @Override
    public void init() throws Exception {
        this.game = new GameInitializer(new GameFactory(ROWS, COLUMNS), getParameters().getRaw()).initGame();
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameView.fxml"));
        final Pane root = fxmlLoader.load();

        final GuiController controller = fxmlLoader.getController();
        controller.initialize(game);
        game.play();

        final Scene scene = new Scene(root);
        stage.setTitle("GSE-2020 Encore!");
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public static void main(final String... args) {
        try {
            launch(args);
        } catch (RuntimeException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof InvalidParameterException) {
                System.exit(EXIT_WRONG_FILE);
            }
            if (cause instanceof InvalidBoardException) {
                System.exit(EXIT_INVALID_BOARD);
            }
            throw e;
        }
    }


}
