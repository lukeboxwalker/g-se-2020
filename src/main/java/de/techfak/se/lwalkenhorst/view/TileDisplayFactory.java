package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.Tile;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import javafx.scene.image.ImageView;

public class TileDisplayFactory {

    private final ImageFactory imageFactory;

    public TileDisplayFactory(final ImageFactory imageFactory) {
        this.imageFactory = imageFactory;
    }

    public TileDisplay createTileView(final int row, final int column, final Board board) {
        final Tile tile = board.getTileAt(row, column);
        final ImageView background = createBackgroundForColor(tile.getColor());
        final ImageView crossImage = imageFactory.createCrossImage();
        final TileDisplay tileDisplay = new TileDisplay(new TilePosition(row, column), background, crossImage);
        if (board.getStartColumn() == column) {
            tileDisplay.getChildren().add(imageFactory.createStartColumnImage());
        }
        tileDisplay.setCrossed(tile.isCrossed());
        return tileDisplay;
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
