package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.TileColor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ColorPointsDisplay extends VBox {

    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;
    private static final int Y_OFFSET = 90;

    private final Map<TileColor, PointDisplay> colorDisplayMap = new HashMap<>();

    public ColorPointsDisplay(final ImageFactory imageFactory) {
        super();
        addPointDisplay(TileColor.GREEN, imageFactory);
        addPointDisplay(TileColor.YELLOW, imageFactory);
        addPointDisplay(TileColor.BLUE, imageFactory);
        addPointDisplay(TileColor.RED, imageFactory);
        addPointDisplay(TileColor.ORANGE, imageFactory);
    }

    public final void addPointDisplay(final TileColor tileColor, final ImageFactory imageFactory) {
        final ImageView crossed = imageFactory.createCrossImage();
        final PointDisplay pointDisplay;
        switch (tileColor) {
            case GREEN:
                pointDisplay = new PointDisplay(imageFactory.createImage(45, Y_OFFSET, WIDTH, HEIGHT), crossed);
                break;
            case YELLOW:
                pointDisplay = new PointDisplay(imageFactory.createImage(90, Y_OFFSET, WIDTH, HEIGHT), crossed);
                break;
            case BLUE:
                pointDisplay = new PointDisplay(imageFactory.createImage(135, Y_OFFSET, WIDTH, HEIGHT), crossed);
                break;
            case RED:
                pointDisplay = new PointDisplay(imageFactory.createImage(180, Y_OFFSET, WIDTH, HEIGHT), crossed);
                break;
            case ORANGE:
                pointDisplay = new PointDisplay(imageFactory.createImage(225, Y_OFFSET, WIDTH, HEIGHT), crossed);
                break;
            default:
                throw new IllegalArgumentException();
        }
        getChildren().add(pointDisplay);
        colorDisplayMap.put(tileColor, pointDisplay);
    }

    public void markColorAsFull(final TileColor color) {
        if (colorDisplayMap.containsKey(color)) {
            colorDisplayMap.get(color).mark();
        }
    }
}
