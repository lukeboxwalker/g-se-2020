package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.DiceColorFace;
import de.techfak.se.lwalkenhorst.game.DiceNumberFace;
import de.techfak.se.lwalkenhorst.game.DiceResult;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DiceDisplay extends HBox {

    private static final String FACE_YELLOW = "/assets/dice/face_yellow.png";
    private static final String FACE_GREEN = "/assets/dice/face_green.png";
    private static final String FACE_RED = "/assets/dice/face_red.png";
    private static final String FACE_BLUE = "/assets/dice/face_blue.png";
    private static final String FACE_ORANGE = "/assets/dice/face_orange.png";
    private static final String FACE_COLOR_JOKER = "/assets/dice/face_color_joker.png";

    private static final String FACE_ONE = "/assets/dice/face_one.png";
    private static final String FACE_TWO = "/assets/dice/face_two.png";
    private static final String FACE_THREE = "/assets/dice/face_three.png";
    private static final String FACE_FOUR = "/assets/dice/face_four.png";
    private static final String FACE_FIVE = "/assets/dice/face_five.png";
    private static final String FACE_NUMBER_JOKER = "/assets/dice/face_number_joker.png";

    private static final double DICE_SPACING = 10;

    private final Map<DiceColorFace, Supplier<ImageView>> diceColorTextures = new HashMap<>();
    private final Map<DiceNumberFace, Supplier<ImageView>> diceNumberTextures = new HashMap<>();

    private final VBox colorDice = new VBox();
    private final VBox numberDice = new VBox();

    public DiceDisplay() {
        super();
        this.colorDice.setSpacing(DICE_SPACING);
        this.numberDice.setSpacing(DICE_SPACING);
        this.getChildren().addAll(List.of(colorDice, numberDice));
        this.setSpacing(DICE_SPACING);
        this.initTextures();
    }

    private void initTextures() {
        diceColorTextures.put(DiceColorFace.YELLOW, TextureUtils.textureSupplier(FACE_YELLOW));
        diceColorTextures.put(DiceColorFace.GREEN, TextureUtils.textureSupplier(FACE_GREEN));
        diceColorTextures.put(DiceColorFace.RED, TextureUtils.textureSupplier(FACE_RED));
        diceColorTextures.put(DiceColorFace.BLUE, TextureUtils.textureSupplier(FACE_BLUE));
        diceColorTextures.put(DiceColorFace.ORANGE, TextureUtils.textureSupplier(FACE_ORANGE));
        diceColorTextures.put(DiceColorFace.JOKER, TextureUtils.textureSupplier(FACE_COLOR_JOKER));

        diceNumberTextures.put(DiceNumberFace.ONE, TextureUtils.textureSupplier(FACE_ONE));
        diceNumberTextures.put(DiceNumberFace.TWO, TextureUtils.textureSupplier(FACE_TWO));
        diceNumberTextures.put(DiceNumberFace.THREE, TextureUtils.textureSupplier(FACE_THREE));
        diceNumberTextures.put(DiceNumberFace.FOUR, TextureUtils.textureSupplier(FACE_FOUR));
        diceNumberTextures.put(DiceNumberFace.FIVE, TextureUtils.textureSupplier(FACE_FIVE));
        diceNumberTextures.put(DiceNumberFace.JOKER, TextureUtils.textureSupplier(FACE_NUMBER_JOKER));
    }

    public void updateDice(final DiceResult diceResult) {
        colorDice.getChildren().clear();
        diceResult.getColors().forEach(colorFace -> {
            if (diceColorTextures.containsKey(colorFace)) {
                colorDice.getChildren().add(diceColorTextures.get(colorFace).get());
            }
        });

        numberDice.getChildren().clear();
        diceResult.getNumbers().forEach(numberFace -> {
            if (diceNumberTextures.containsKey(numberFace)) {
                numberDice.getChildren().add(diceNumberTextures.get(numberFace).get());
            }
        });
    }
}
