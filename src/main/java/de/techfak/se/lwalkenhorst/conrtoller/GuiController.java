package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.PropertyChange;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import de.techfak.se.lwalkenhorst.game.Turn;
import de.techfak.se.lwalkenhorst.game.TurnFactory;
import de.techfak.se.lwalkenhorst.view.ChatDisplay;
import de.techfak.se.lwalkenhorst.view.GameDisplay;
import de.techfak.se.lwalkenhorst.view.GameOverScreen;
import de.techfak.se.lwalkenhorst.view.ImageFactory;
import de.techfak.se.lwalkenhorst.view.TileDisplay;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GuiController {

    private static final String BUTTON_PASS = "Pass";
    private static final String BUTTON_SUBMIT = "Submit";

    @FXML
    public AnchorPane root;

    @FXML
    private GameDisplay gameDisplay;

    @FXML
    private ChatDisplay chatDisplay;

    private Game game;
    private final List<TileDisplay> clickedTiles = new ArrayList<>();
    private final TurnFactory turnFactory = new TurnFactory();

    public void initialize(final Game game) throws IOException {
        chatDisplay.info("Starting new Game");
        root.setBackground(new ImageFactory().createBackgroundImage(root.getWidth(), root.getHeight()));
        this.game = game;
        this.game.addListener(PropertyChange.SCORE, event -> Platform.runLater(this::updatePoints));
        this.game.addListener(PropertyChange.ROUND, event -> Platform.runLater(this::updateDice));
        this.game.addListener(PropertyChange.FINISHED, event -> Platform.runLater(this::displayGameFinished));
        gameDisplay.init(game);
        gameDisplay.setTileClickHandler(this::clickTile);
        gameDisplay.setSubmitTurnHandler(this::submitTurn);
        gameDisplay.updateButtonText(BUTTON_PASS);
    }

    private void displayGameFinished() {
        root.setDisable(true);
        root.getChildren().add(new GameOverScreen(game.getPoints(), root));
    }

    private void updateDice() {
        gameDisplay.updateDice(game.getDiceResult());
    }

    private void updatePoints() {
        gameDisplay.setPoints(game.getPoints());
        for (final int column : game.getRuleManger().getFullColumns()) {
            gameDisplay.markColumnPoints(column);
        }
        for (final TileColor color : game.getRuleManger().getFullColors()) {
            gameDisplay.markColorAsFull(color);
        }
    }

    private void clickTile(final TileDisplay tileDisplay) {
        if (!game.getBoard().getTileAt(tileDisplay.getPosition()).isCrossed()) {
            gameDisplay.updateButtonText(BUTTON_SUBMIT);
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
        gameDisplay.updateButtonText(BUTTON_PASS);
        clickedTiles.clear();
    }
}
