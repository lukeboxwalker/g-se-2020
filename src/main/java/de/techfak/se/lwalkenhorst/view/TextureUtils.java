package de.techfak.se.lwalkenhorst.view;

import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.Supplier;

import static javafx.scene.layout.BackgroundRepeat.REPEAT;

public final class TextureUtils {

    private TextureUtils() {
        super();
    }

    private static Image loadImage(final String resource) {
        try (InputStream inputStream = TextureUtils.class.getResourceAsStream(resource)) {
            return new Image(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static ImageView loadTexture(final String resource) {
        return new ImageView(loadImage(resource));
    }

    public static Supplier<ImageView> textureSupplier(final String resource) {
        return () -> loadTexture(resource);
    }

    public static Background createBackgroundImage(final String resource, final double width, final double height) {
        final Image image = loadImage(resource);
        final BackgroundPosition position = new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 0, false);
        final BackgroundSize size = new BackgroundSize(width, height, false, false, false, true);
        final BackgroundImage backgroundImage = new BackgroundImage(image, REPEAT, REPEAT, position, size);
        return new Background(backgroundImage);
    }
}
