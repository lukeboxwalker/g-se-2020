package de.techfak.se.lwalkenhorst.view;

import de.techfak.se.lwalkenhorst.game.Board;
import de.techfak.se.lwalkenhorst.game.Tile;
import de.techfak.se.lwalkenhorst.game.TileColor;
import de.techfak.se.lwalkenhorst.game.TilePosition;
import javafx.scene.image.ImageView;

public class TileDisplayFactory {

    private static final String YELLOW = "/assets/tile/yellow.png";
    private static final String GREEN = "/assets/tile/green.png";
    private static final String BLUE = "/assets/tile/blue.png";
    private static final String RED = "/assets/tile/red.png";
    private static final String ORANGE = "/assets/tile/orange.png";

    private static final String CROSS = "/assets/tile/cross.png";
    private static final String START_COL = "/assets/tile/highlight.png";

    public TileDisplay createTileView(final int row, final int column, final Board board) {
        final Tile tile = board.getTileAt(row, column);
        final ImageView background = createBackgroundForColor(tile.getColor());
        final ImageView crossImage = TextureUtils.loadTexture(CROSS);
        final TileDisplay tileDisplay = new TileDisplay(new TilePosition(row, column), background, crossImage);
        if (board.getStartColumn() == column) {
            tileDisplay.getChildren().add(TextureUtils.loadTexture(START_COL));
        }
        tileDisplay.setCrossed(tile.isCrossed());
        return tileDisplay;
    }

    public ImageView createBackgroundForColor(final TileColor color) {
        switch (color) {
            case RED:
                return TextureUtils.loadTexture(RED);
            case BLUE:
                return TextureUtils.loadTexture(BLUE);
            case GREEN:
                return TextureUtils.loadTexture(GREEN);
            case ORANGE:
                return TextureUtils.loadTexture(ORANGE);
            case YELLOW:
                return TextureUtils.loadTexture(YELLOW);
            default:
                throw new IllegalArgumentException();
        }
    }

}
