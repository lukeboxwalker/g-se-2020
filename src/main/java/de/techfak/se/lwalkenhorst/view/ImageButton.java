package de.techfak.se.lwalkenhorst.view;

import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static javafx.scene.paint.Color.BLACK;


public class ImageButton extends Pane {

    private static final int SHADOW_WIDTH = 20;

    public ImageButton(final ImageView buttonImage) {
        super(buttonImage);
        setOnMousePressed(event -> buttonImage.setEffect(new InnerShadow(SHADOW_WIDTH, BLACK)));
        setOnMouseReleased(event -> buttonImage.setEffect(null));
    }
}
