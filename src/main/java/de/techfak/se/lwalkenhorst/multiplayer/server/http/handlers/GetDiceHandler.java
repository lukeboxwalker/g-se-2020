package de.techfak.se.lwalkenhorst.multiplayer.server.http.handlers;

import de.techfak.se.lwalkenhorst.multiplayer.game.BaseGame;
import de.techfak.se.lwalkenhorst.multiplayer.game.PlayerName;
import de.techfak.se.lwalkenhorst.multiplayer.server.http.response_body.DiceResponse;
import io.javalin.http.Context;

/**
 * Handler to get the current rolled dice.
 */
public class GetDiceHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetDiceHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName player) {
        final DiceResponse response = new DiceResponse(game.getDiceResult());
        ctx.status(STATUS_OK).json(response);
    }
}
