package de.techfak.se.lwalkenhorst.domain.controller;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Color;
import de.techfak.se.lwalkenhorst.domain.DiceResult;
import de.techfak.se.lwalkenhorst.domain.Game;
import de.techfak.se.lwalkenhorst.domain.GameObserver;
import de.techfak.se.lwalkenhorst.domain.Position;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.paint.Color.WHITE;

public class GameController implements GameObserver {

    private static final int PANE_SIZE = 100;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox rootBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox box;

    @FXML
    private Circle dice1;

    @FXML
    private Circle dice2;

    @FXML
    private Circle dice3;

    @FXML
    private Text dice4;

    @FXML
    private Text dice5;

    @FXML
    private Text dice6;

    @FXML
    private Button confirmButton;

    private final Map<Circle, Color> colorDice = new HashMap<>();
    private final Map<Text, Integer> numberDice = new HashMap<>();
    private final View generator = new View();
    private final VBox progressIndicator = new VBox(new ProgressIndicator());
    private boolean gameOver = false;

    private StackPane[][] tiles;
    private List<Position> crossedPositions;

    public void init(final Game gameModel) {
        this.crossedPositions = new ArrayList<>();
        this.progressIndicator.prefHeightProperty().bind(root.prefHeightProperty());
        this.progressIndicator.prefWidthProperty().bind(root.prefWidthProperty());
        this.progressIndicator.setStyle("-fx-background-color: rgba(64, 64, 64, 0.5)");
        this.progressIndicator.setAlignment(Pos.CENTER);
        gameModel.addListener(this);
        this.box.setFillHeight(true);
        this.confirmButton.setOnMouseClicked(event -> {
            if (crossedPositions.isEmpty()) {
                gameModel.pass();
                if (!gameOver) {
                    rootBox.setDisable(true);
                    root.getChildren().add(progressIndicator);
                }
            }
            if (gameModel.crossTiles(crossedPositions)) {
                if (!gameOver) {
                    rootBox.setDisable(true);
                    root.getChildren().add(progressIndicator);
                }
            } else {
                crossedPositions.forEach(position ->
                        generator.removeCrossFromPane(tiles[position.getPosX()][position.getPosY()]));
                crossedPositions.clear();
            }
        });
    }

    private void viewColorDiceResult(List<Color> possiblePicks, Map<Circle, Color> map, Circle... dice) {
        if (possiblePicks.size() == 3 && dice.length == 3) {
            dice[0].setStyle("-fx-fill: " + possiblePicks.get(0).getFxName());
            map.put(dice[0], possiblePicks.get(0));
            dice[1].setStyle("-fx-fill: " + possiblePicks.get(1).getFxName());
            map.put(dice[1], possiblePicks.get(1));
            dice[2].setStyle("-fx-fill: " + possiblePicks.get(2).getFxName());
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
        Platform.runLater(() -> {
            this.tiles = generator.createGrid(board, gridPane, PANE_SIZE, this::clickTile);
        });
    }

    @Override
    public void onDiceRoll(final DiceResult diceResult) {
        Platform.runLater(() -> {
            rootBox.setDisable(false);
            root.getChildren().remove(progressIndicator);
            viewColorDiceResult(diceResult.getRolledColors(), colorDice, dice1, dice2, dice3);
            viewNumberDiceResult(diceResult.getRolledNumbers(), numberDice, dice4, dice5, dice6);
        });
    }

    @Override
    public void onTilesCross(final List<Position> positions) {
        Platform.runLater(() -> {
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
        });
    }

    @Override
    public void onGameEnd(final int points) {
        this.gameOver = true;
        Platform.runLater(() -> {
            final Text text = new Text("Game over Points: " + points);
            text.setFill(WHITE);
            text.setStyle("-fx-font-size: 40");
            final VBox gameOverScreen = new VBox(text);
            gameOverScreen.prefHeightProperty().bind(root.prefHeightProperty());
            gameOverScreen.prefWidthProperty().bind(root.prefWidthProperty());
            gameOverScreen.setStyle("-fx-background-color: rgba(64, 64, 64, 0.5)");
            gameOverScreen.setAlignment(Pos.CENTER);
            rootBox.setDisable(true);
            root.getChildren().add(gameOverScreen);
        });
    }
}
