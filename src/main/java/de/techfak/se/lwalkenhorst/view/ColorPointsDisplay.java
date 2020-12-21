package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.TileColor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ColorPointsDisplay extends VBox {

    private static final String FULL_YELLOW = "/assets/points/full_yellow.png";
    private static final String FULL_GREEN = "/assets/points/full_green.png";
    private static final String FULL_BLUE = "/assets/points/full_blue.png";
    private static final String FULL_RED = "/assets/points/full_red.png";
    private static final String FULL_ORANGE = "/assets/points/full_orange.png";

    private static final String MARK_CIRCLE = "/assets/points/mark_circle.png";

    private final Map<TileColor, PointDisplay> colorDisplayMap = new HashMap<>();

    public ColorPointsDisplay() {
        super();
        final ImageView marked = TextureUtils.loadTexture(MARK_CIRCLE);
        addPointDisplay(TileColor.GREEN, TextureUtils.loadTexture(FULL_GREEN), marked);
        addPointDisplay(TileColor.YELLOW, TextureUtils.loadTexture(FULL_YELLOW), marked);
        addPointDisplay(TileColor.BLUE, TextureUtils.loadTexture(FULL_BLUE), marked);
        addPointDisplay(TileColor.RED, TextureUtils.loadTexture(FULL_RED), marked);
        addPointDisplay(TileColor.ORANGE, TextureUtils.loadTexture(FULL_ORANGE), marked);
    }

    public final void addPointDisplay(final TileColor tileColor, final ImageView texture, final ImageView marked) {
        final PointDisplay pointDisplay = new PointDisplay(texture, marked);
        getChildren().add(pointDisplay);
        colorDisplayMap.put(tileColor, pointDisplay);
    }

    public void markColorAsFull(final TileColor color) {
        if (colorDisplayMap.containsKey(color)) {
            colorDisplayMap.get(color).mark();
        }
    }
}
