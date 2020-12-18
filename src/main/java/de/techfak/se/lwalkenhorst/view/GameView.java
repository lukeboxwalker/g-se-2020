package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Game;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GameView extends VBox {

    private final Button submitButton;
    private final BoardView boardView;
    private final PointsView pointsView;

    public GameView(final Game game) {
        super();
        this.submitButton = new Button("Sumbit");
        final ImageFactory imageFactory = new ImageFactory();
        this.boardView = new BoardView(game.getBoard(), imageFactory);
        this.pointsView = new PointsView(game.getBoard(), game.getRuleManger(), imageFactory);
        getChildren().add(imageFactory.createBoardHeaderImage());
        getChildren().add(boardView);
        final HBox hBox = new HBox(pointsView, submitButton);
        getChildren().add(hBox);

    }

    public void setSubmitTurnHandler(final TurnSubmitHandler eventHandler) {
        this.submitButton.setOnMouseClicked(event -> eventHandler.handle());
    }

    public void setTileClickHandler(final TileClickHandler eventHandler) {
        boardView.registerClickHandler(eventHandler);
    }
}
