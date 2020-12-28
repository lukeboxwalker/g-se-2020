package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.PropertyChange;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import de.techfak.se.lwalkenhorst.game.Turn;
import de.techfak.se.lwalkenhorst.game.TurnFactory;
import de.techfak.se.lwalkenhorst.view.BoardDisplay;
import de.techfak.se.lwalkenhorst.view.ChatDisplay;
import de.techfak.se.lwalkenhorst.view.ColorPointsDisplay;
import de.techfak.se.lwalkenhorst.view.ColumnPointsDisplay;
import de.techfak.se.lwalkenhorst.view.DiceDisplay;
import de.techfak.se.lwalkenhorst.view.GameOverScreen;
import de.techfak.se.lwalkenhorst.view.TextureUtils;
import de.techfak.se.lwalkenhorst.view.TileDisplay;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GuiController {

    private static final String BUTTON_PASS = "Pass";
    private static final String BUTTON_SUBMIT = "Submit";

    @FXML
    private AnchorPane root;

    @FXML
    private BoardDisplay boardDisplay;

    @FXML
    private DiceDisplay diceDisplay;

    @FXML
    private ColorPointsDisplay colorPointsDisplay;

    @FXML
    private ColumnPointsDisplay columnPointsDisplay;

    @FXML
    private Button submitButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private ChatDisplay chatDisplay;

    private Game game;
    private final List<TileDisplay> clickedTiles = new ArrayList<>();
    private final TurnFactory turnFactory = new TurnFactory();

    public void initialize(final Game game) {
        this.game = game;
        chatDisplay.info("Starting new Game");
        root.setBackground(TextureUtils.createBackgroundImage("/assets/background.png", root.getWidth(), root.getHeight()));
        game.addListener(PropertyChange.SCORE, event -> Platform.runLater(this::updatePoints));
        game.addListener(PropertyChange.ROUND, event -> Platform.runLater(this::updateDice));
        game.addListener(PropertyChange.FINISHED, event -> Platform.runLater(this::displayGameFinished));
        boardDisplay.init(game.getBoard());
        columnPointsDisplay.init(game.getBoard(), game.getRuleManger());
        boardDisplay.registerClickHandler(this::clickTile);
        submitButton.setOnMouseClicked(event -> submitTurn());
        submitButton.setText(BUTTON_PASS);
    }

    private void displayGameFinished() {
        root.setDisable(true);
        root.getChildren().add(new GameOverScreen(game.getPoints(), root));
    }

    private void updateDice() {
        diceDisplay.updateDice(game.getDiceResult());
    }

    private void updatePoints() {
        scoreLabel.setText("Score: " + game.getPoints());
        for (final int column : game.getRuleManger().getFullColumns()) {
            columnPointsDisplay.markColumnAsFiull(column);
        }
        for (final TileColor color : game.getRuleManger().getFullColors()) {
            colorPointsDisplay.markColorAsFull(color);
        }
    }

    private void clickTile(final TileDisplay tileDisplay) {
        if (!game.getBoard().getTileAt(tileDisplay.getPosition()).isCrossed()) {
            submitButton.setText(BUTTON_SUBMIT);
            clickedTiles.add(tileDisplay);
            tileDisplay.setCrossed(true);
        }
    }

    private void submitTurn() {
        final List<TilePosition> positions = clickedTiles.stream()
            .map(TileDisplay::getPosition).collect(Collectors.toList());
        final Turn turn = turnFactory.createTurn(positions);

        try {
            game.applyTurn(turn);
        } catch (InvalidTurnException e) {
            chatDisplay.warn(e.getMessage());
            clickedTiles.forEach(tileDisplay -> tileDisplay.setCrossed(false));
        }
        submitButton.setText(BUTTON_PASS);
        clickedTiles.clear();
    }
}
