package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.TilePosition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class TileView extends StackPane {

    private final TilePosition position;
    private final ImageView crossImage;
    private final List<TileClickHandler> clickHandlers = new ArrayList<>();

    public TileView(final TilePosition position, final ImageView backgroundImage, final ImageView crossImage) {
        super();
        this.position = position;
        this.crossImage = crossImage;
        getChildren().add(backgroundImage);
        setOnMouseClicked(event -> clickHandlers.forEach(clickHandler -> clickHandler.handle(this)));
    }

    public void setCrossed(final boolean crossed) {
        if (crossed && !getChildren().contains(crossImage)) {
            getChildren().add(crossImage);
        } else if (!crossed) {
            getChildren().remove(crossImage);
        }
    }

    public void registerClickHandler(final TileClickHandler clickHandler) {
        this.clickHandlers.add(clickHandler);
    }

    public TilePosition getPosition() {
        return position;
    }
}
