package de.techfak.se.lwalkenhorst.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class ImageFactory {

    private static final int WIDTH = 45;
    private static final int HEIGHT = 45;
    private static final int YELLOW_OFFSET = 0;
    private static final int GREEN_OFFSET = 45;
    private static final int ORANGE_OFFSET = 90;
    private static final int RED_OFFSET = 135;
    private static final int BLUE_OFFSET = 180;
    private static final int CROSS_OFFSET = 225;
    private static final int START_COLUMN_OFFSET = 270;

    private final PixelReader reader;

    public ImageFactory() {
        this.reader = new Image("/images/assets.png").getPixelReader();
    }

    public ImageView createYellowBackground() {
        return new ImageView(new WritableImage(reader, YELLOW_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createGreenBackground() {
        return new ImageView(new WritableImage(reader, GREEN_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createOrangeBackground() {
        return new ImageView(new WritableImage(reader, ORANGE_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createRedBackground() {
        return new ImageView(new WritableImage(reader, RED_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createBlueBackground() {
        return new ImageView(new WritableImage(reader, BLUE_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createCrossImage() {
        return new ImageView(new WritableImage(reader, CROSS_OFFSET, 0, WIDTH, HEIGHT));
    }

    public ImageView createStartColumnImage() {
        return new ImageView(new WritableImage(reader, START_COLUMN_OFFSET, 0, WIDTH, HEIGHT));
    }
}
