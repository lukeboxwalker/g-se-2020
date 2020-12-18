package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.DiceColorFace;
import de.techfak.se.lwalkenhorst.game.DiceNumberFace;
import de.techfak.se.lwalkenhorst.game.DiceResult;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DiceDisplay extends Group {

    private static final int FACE_SIZE = 77;
    private static final int COLOR_Y_OFFSET = 135;
    private static final int NUMBER_Y_OFFSET = 212;

    private static final double DICE_SPACING = 10;

    private final Map<DiceColorFace, Supplier<ImageView>> diceColorTextures = new HashMap<>();
    private final Map<DiceNumberFace, Supplier<ImageView>> diceNumberTextures = new HashMap<>();

    private final VBox colorDice = new VBox();
    private final VBox numberDice = new VBox();

    public DiceDisplay(final ImageFactory imageFactory) {
        super();
        colorDice.setSpacing(DICE_SPACING);
        numberDice.setSpacing(DICE_SPACING);
        final HBox hBox = new HBox(colorDice, numberDice);
        hBox.setSpacing(DICE_SPACING);
        getChildren().add(hBox);
        diceColorTextures.put(DiceColorFace.YELLOW, () -> imageFactory.createImage(0, COLOR_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceColorTextures.put(DiceColorFace.GREEN, () -> imageFactory.createImage(FACE_SIZE, COLOR_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceColorTextures.put(DiceColorFace.RED, () -> imageFactory.createImage(2 * FACE_SIZE, COLOR_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceColorTextures.put(DiceColorFace.BLUE, () -> imageFactory.createImage(3 * FACE_SIZE, COLOR_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceColorTextures.put(DiceColorFace.ORANGE, () -> imageFactory.createImage(4 * FACE_SIZE, COLOR_Y_OFFSET, FACE_SIZE, FACE_SIZE));

        diceNumberTextures.put(DiceNumberFace.ONE, () -> imageFactory.createImage(0, NUMBER_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceNumberTextures.put(DiceNumberFace.TWO, () -> imageFactory.createImage(FACE_SIZE, NUMBER_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceNumberTextures.put(DiceNumberFace.THREE, () -> imageFactory.createImage(2 * FACE_SIZE, NUMBER_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceNumberTextures.put(DiceNumberFace.FOUR, () -> imageFactory.createImage(3 * FACE_SIZE, NUMBER_Y_OFFSET, FACE_SIZE, FACE_SIZE));
        diceNumberTextures.put(DiceNumberFace.FIVE, () -> imageFactory.createImage(4 * FACE_SIZE, NUMBER_Y_OFFSET, FACE_SIZE, FACE_SIZE));
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
