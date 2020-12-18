package de.techfak.se.lwalkenhorst.conrtoller;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;
import de.techfak.se.lwalkenhorst.game.Game;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import de.techfak.se.lwalkenhorst.game.Turn;
import de.techfak.se.lwalkenhorst.game.TurnFactory;
import de.techfak.se.lwalkenhorst.view.GameView;
import de.techfak.se.lwalkenhorst.view.TileView;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ViewController {

    @FXML
    private VBox rootBox;

    private Game game;
    private final List<TileView> clickedTiles = new ArrayList<>();
    private final TurnFactory turnFactory = new TurnFactory();

    public void initialize(final Game game) {
        this.game = game;
        final GameView gameView = new GameView(game);
        gameView.setTileClickHandler(this::clickTile);
        gameView.setSubmitTurnHandler(this::submitTurn);
        rootBox.getChildren().add(gameView);
        game.play();
    }

    private void clickTile(final TileView tileView) {
        if (!game.getBoard().getTileAt(tileView.getPosition()).isCrossed()) {
            clickedTiles.add(tileView);
            tileView.setCrossed(true);
        }
    }

    private void submitTurn() {
        final List<TilePosition> positions = clickedTiles.stream()
            .map(TileView::getPosition).collect(Collectors.toList());
        final Turn turn = turnFactory.createTurn(positions);

        try {
            game.applyTurn(turn);
        } catch (InvalidTurnException e) {
            clickedTiles.forEach(tileView -> tileView.setCrossed(false));
        }

        clickedTiles.clear();
    }
}
