package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.TilePosition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.function.Consumer;

public class TileView extends StackPane {

    private final ImageView crossImage;
    private final TilePosition position;

    public TileView(final TilePosition position, final ImageView backgroundImage, final ImageView crossImage) {
        this.position = position;
        this.crossImage = crossImage;
        getChildren().add(backgroundImage);
        setOnMouseClicked(event -> setCrossed(!getChildren().contains(crossImage)));
    }

    public void setCrossed(final boolean crossed) {
        if (crossed && !getChildren().contains(crossImage)) {
            getChildren().add(crossImage);
        } else if (!crossed) {
            getChildren().remove(crossImage);
        }
    }

    public void registerClickListener(final Consumer<TilePosition> listener) {
        this.setOnMouseClicked((event) -> listener.accept(position));
    }
}
