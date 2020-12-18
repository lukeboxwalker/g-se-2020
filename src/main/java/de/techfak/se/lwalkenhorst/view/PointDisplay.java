package de.techfak.se.lwalkenhorst.view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PointDisplay extends StackPane {

    private final ImageView markedImage;

    public PointDisplay(final ImageView background, final ImageView markedImage) {
        super(background);
        this.markedImage = markedImage;
    }

    public void mark() {
        if (!getChildren().contains(markedImage)) {
            getChildren().add(markedImage);
        }
    }
}
