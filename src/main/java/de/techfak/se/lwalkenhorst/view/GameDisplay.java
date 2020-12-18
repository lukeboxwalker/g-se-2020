package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Game;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GameDisplay extends VBox {

    private Button submitButton;
    private BoardDisplay boardDisplay;
    private ColumnPointsDisplay columnPointsDisplay;

    public void init(final Game game) {
        this.submitButton = new Button("Sumbit");
        final ImageFactory imageFactory = new ImageFactory();
        this.boardDisplay = new BoardDisplay(game.getBoard(), imageFactory);
        this.columnPointsDisplay = new ColumnPointsDisplay(game.getBoard(), game.getRuleManger(), imageFactory);
        getChildren().add(imageFactory.createBoardHeaderImage());
        getChildren().add(boardDisplay);
        final HBox hBox = new HBox(columnPointsDisplay, submitButton);
        getChildren().add(hBox);
    }

    public void setSubmitTurnHandler(final TurnSubmitHandler eventHandler) {
        this.submitButton.setOnMouseClicked(event -> eventHandler.handle());
    }

    public void setTileClickHandler(final TileClickHandler eventHandler) {
        boardDisplay.registerClickHandler(eventHandler);
    }

    public void markColumnPoints(final int column) {
        columnPointsDisplay.mark(column);
    }
}
