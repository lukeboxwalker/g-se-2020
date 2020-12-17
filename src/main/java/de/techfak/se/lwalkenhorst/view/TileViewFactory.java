package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Tile;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import javafx.scene.image.ImageView;

public class TileViewFactory {

    private final ImageFactory imageFactory;

    public TileViewFactory(final ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    public TileView createTileView(final int row, final int column, final Tile tile) {
        final ImageView background = createBackgroundForColor(tile.getColor());
        final ImageView crossImage = imageFactory.createCrossImage();
        final TileView tileView = new TileView(new TilePosition(row, column), background, crossImage);
        tileView.setCrossed(tile.isCrossed());
        return tileView;
    }

    public ImageView createBackgroundForColor(final TileColor color) {
        switch (color) {
            case RED:
                return imageFactory.createRedBackground();
            case BLUE:
                return imageFactory.createBlueBackground();
            case GREEN:
                return imageFactory.createGreenBackground();
            case ORANGE:
                return imageFactory.createOrangeBackground();
            case YELLOW:
                return imageFactory.createYellowBackground();
            default:
                throw new IllegalArgumentException();
        }
    }

}
