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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    public Text points;

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

    private FxBoard fxBoard;
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
            } else if (gameModel.crossTiles(crossedPositions)) {
                if (!gameOver) {
                    rootBox.setDisable(true);
                    root.getChildren().add(progressIndicator);
                }
            } else {
                crossedPositions.forEach(fxBoard::removeCrossFromTile);
                crossedPositions.clear();
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Rule violation.");
                alert.setContentText("Could not cross these tile(s). Stick to the rules!");
                alert.showAndWait();
            }
        });
    }

    private void viewColorDiceResult(final List<Color> list, final Map<Circle, Color> map, final Circle... dice) {
        if (list.size() == 3 && dice.length == 3) {
            dice[0].setStyle("-fx-fill: " + list.get(0).getFxName());
            map.put(dice[0], list.get(0));
            dice[1].setStyle("-fx-fill: " + list.get(1).getFxName());
            map.put(dice[1], list.get(1));
            dice[2].setStyle("-fx-fill: " + list.get(2).getFxName());
            map.put(dice[2], list.get(2));
        }
    }

    private void viewNumberDiceResult(final List<Integer> list, final Map<Text, Integer> map, final Text... dice) {
        if (list.size() == 3 && dice.length == 3) {
            dice[0].setText(String.valueOf(list.get(0)));
            map.put(dice[0], list.get(0));
            dice[1].setText(String.valueOf(list.get(1)));
            map.put(dice[1], list.get(1));
            dice[2].setText(String.valueOf(list.get(2)));
            map.put(dice[2], list.get(2));
        }
    }

    private void clickTile(final int posX, final int posY) {
        final Position position = new Position(posX, posY);
        if (!crossedPositions.contains(position)) {
            crossedPositions.add(position);
            fxBoard.addCrossToTile(position);
        }
    }

    @Override
    public void onGameStart(final Board board) {
        Platform.runLater(() -> {
            this.fxBoard = generator.createGrid(board, gridPane, PANE_SIZE, this::clickTile);
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
                if (crossedPositions.contains(position)) {
                    crossedPositions.remove(position);
                } else {
                    fxBoard.addCrossToTile(position);
                }
            });
            crossedPositions.forEach(fxBoard::removeCrossFromTile);
            crossedPositions.clear();
        });
    }

    @Override
    public void onPointsChange(final int points, final List<Integer> fullColumns) {
        Platform.runLater(() -> {
            this.points.setText("Points: " + points);
            fullColumns.forEach(fxBoard::markPointsInCol);
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
