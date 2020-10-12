package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Color;
import de.techfak.se.lwalkenhorst.domain.DiceResult;
import de.techfak.se.lwalkenhorst.domain.Game;
import de.techfak.se.lwalkenhorst.domain.GameObserver;
import de.techfak.se.lwalkenhorst.domain.Position;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController implements GameObserver {

    private static final int PANE_SIZE = 100;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox box;

    @FXML
    private Text dice1;

    @FXML
    private Text dice2;

    @FXML
    private Text dice3;

    @FXML
    private Text dice4;

    @FXML
    private Text dice5;

    @FXML
    private Text dice6;

    @FXML
    private Label pointsLabel;

    @FXML
    private Button confirmButton;


    private final Map<Text, Color> colorDice = new HashMap<>();
    private final Map<Text, Integer> numberDice = new HashMap<>();
    private final View generator = new View();

    private Game gameModel;
    private StackPane[][] tiles;
    private List<Position> crossedPositions;

    public void init(final Game gameModel) {
        this.crossedPositions = new ArrayList<>();
        this.gameModel = gameModel;
        this.gameModel.addListener(this);
        this.box.setFillHeight(true);
        this.confirmButton.setOnMouseClicked(event -> {
            if (crossedPositions.isEmpty()) {
                gameModel.pass();
            }
            if (!gameModel.crossTiles(crossedPositions)) {
                crossedPositions.forEach(position ->
                        generator.removeCrossFromPane(tiles[position.getPosX()][position.getPosY()]));
                crossedPositions.clear();
            }
        });
    }

    private void viewColorDiceResult(List<Color> possiblePicks, Map<Text, Color> map, Text... dice) {
        if (possiblePicks.size() == 3 && dice.length == 3) {
            dice[0].setText(possiblePicks.get(0).name());
            map.put(dice[0], possiblePicks.get(0));
            dice[1].setText(possiblePicks.get(1).name());
            map.put(dice[1], possiblePicks.get(1));
            dice[2].setText(possiblePicks.get(2).name());
            map.put(dice[2], possiblePicks.get(2));
        }
    }

    private void viewNumberDiceResult(List<Integer> possiblePicks, Map<Text, Integer> map, Text... dice) {
        if (possiblePicks.size() == 3 && dice.length == 3) {
            dice[0].setText(String.valueOf(possiblePicks.get(0)));
            map.put(dice[0], possiblePicks.get(0));
            dice[1].setText(String.valueOf(possiblePicks.get(1)));
            map.put(dice[1], possiblePicks.get(1));
            dice[2].setText(String.valueOf(possiblePicks.get(2)));
            map.put(dice[2], possiblePicks.get(2));
        }
    }

    private void clickTile(int x, int y) {
        final Position position = new Position(x, y);
        if (!crossedPositions.contains(position)) {
            crossedPositions.add(position);
            generator.addCrossToPane(PANE_SIZE, tiles[x][y]);
        }
    }

    @Override
    public void onGameStart(final Board board) {
        this.tiles = generator.createGrid(board, gridPane, PANE_SIZE, this::clickTile);
        pointsLabel.setText("Points: 0");
    }

    @Override
    public void onDiceRoll(final DiceResult diceResult) {
        viewColorDiceResult(diceResult.getRolledColors(), colorDice, dice1, dice2, dice3);
        viewNumberDiceResult(diceResult.getRolledNumbers(), numberDice, dice4, dice5, dice6);
    }

    @Override
    public void onTilesCross(final List<Position> positions) {
        positions.forEach(position -> {
            if (!crossedPositions.contains(position)) {
                generator.addCrossToPane(PANE_SIZE, tiles[position.getPosX()][position.getPosY()]);
            } else {
                crossedPositions.remove(position);
            }
        });
        crossedPositions.forEach(position ->
                generator.removeCrossFromPane(tiles[position.getPosX()][position.getPosY()]));
        crossedPositions.clear();
    }

    @Override
    public void onGameEnd(final int points) {
        pointsLabel.setText("Points: " + points);
    }
}
