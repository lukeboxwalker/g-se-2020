package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.Points;
import de.techfak.se.lwalkenhorst.game.TileColor;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class GameDisplay extends VBox {

    private static final int SPACING = 45;
    private static final int BUTTON_WIDTH = 135;
    private static final int TEXT_FONT = 20;

    private Button submitButton;
    private BoardDisplay boardDisplay;
    private ColumnPointsDisplay columnPointsDisplay;
    private ColorPointsDisplay colorPointsDisplay;
    private Label pointsLabel;

    public void init(final Game game) {
        final ImageFactory imageFactory = new ImageFactory();
        this.pointsLabel = createPointsLabel();
        setPoints(game.getPoints());
        this.submitButton = createSubmitButton();
        this.boardDisplay = new BoardDisplay(game.getBoard(), imageFactory);
        this.columnPointsDisplay = new ColumnPointsDisplay(game.getBoard(), game.getRuleManger(), imageFactory);
        getChildren().add(imageFactory.createBoardHeaderImage());

        this.colorPointsDisplay = new ColorPointsDisplay(imageFactory);
        HBox hBox = new HBox(boardDisplay, colorPointsDisplay);
        hBox.setSpacing(SPACING);
        getChildren().add(hBox);

        hBox = new HBox(columnPointsDisplay, submitButton, pointsLabel);
        hBox.setSpacing(SPACING);
        getChildren().add(hBox);
    }

    private Button createSubmitButton() {
        final Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(SPACING);
        submitButton.setPrefWidth(BUTTON_WIDTH);
        submitButton.setFont(Font.font("arial", FontWeight.BOLD, TEXT_FONT));
        submitButton.setStyle("-fx-base: white; -fx-border-color: black; -fx-focus-color: transparent;");
        return submitButton;
    }
    private Label createPointsLabel() {
        final Label label = new Label();
        label.setPrefHeight(SPACING);
        label.setPrefWidth(BUTTON_WIDTH);
        label.setFont(Font.font("arial", FontWeight.BOLD, TEXT_FONT));
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-focus-color: transparent;");
        return label;
    }

    public void setSubmitTurnHandler(final TurnSubmitHandler eventHandler) {
        this.submitButton.setOnMouseClicked(event -> eventHandler.handle());
    }

    public void setTileClickHandler(final TileClickHandler eventHandler) {
        boardDisplay.registerClickHandler(eventHandler);
    }

    public void setPoints(final Points points) {
        pointsLabel.setText("Points: " + points);
    }

    public void markColorAsFull(final TileColor color) {
        colorPointsDisplay.markColorAsFull(color);
    }

    public void markColumnPoints(final int column) {
        columnPointsDisplay.mark(column);
    }
}
