package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import de.techfak.se.lwalkenhorst.game.Turn;
import de.techfak.se.lwalkenhorst.game.TurnFactory;
import de.techfak.se.lwalkenhorst.view.GameDisplay;
import de.techfak.se.lwalkenhorst.view.TileDisplay;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ViewController {

    @FXML
    private GameDisplay gameDisplay;

    private Game game;
    private final List<TileDisplay> clickedTiles = new ArrayList<>();
    private final TurnFactory turnFactory = new TurnFactory();

    public void initialize(final Game game) {
        this.game = game;
        this.game.addPropertyChangeListener("points", event -> updatePoints());
        gameDisplay.init(game);
        gameDisplay.setTileClickHandler(this::clickTile);
        gameDisplay.setSubmitTurnHandler(this::submitTurn);
        game.play();
    }

    private void updatePoints() {
        for (int column : game.getRuleManger().getFullColumns()) {
            gameDisplay.markColumnPoints(column);
        }
    }

    private void clickTile(final TileDisplay tileDisplay) {
        if (!game.getBoard().getTileAt(tileDisplay.getPosition()).isCrossed()) {
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
            clickedTiles.forEach(tileDisplay -> tileDisplay.setCrossed(false));
        }

        clickedTiles.clear();
    }
}
