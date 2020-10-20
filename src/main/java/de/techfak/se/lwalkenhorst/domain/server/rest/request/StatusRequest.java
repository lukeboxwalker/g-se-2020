package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.rest.ResponseUtils;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponseBody;
import fi.iki.elonen.NanoHTTPD;

public class StatusRequest implements GetRequest {

    private final StatusResponseBody statusResponseBody = new StatusResponseBody();

    @Override
    public NanoHTTPD.Response handle(GameServer server) {
        statusResponseBody.setGameFinished(server.isGameFinished());
        statusResponseBody.setPlayers(server.getPlayers());
        statusResponseBody.setRound(server.getRound());
        statusResponseBody.setDiceResult(server.getDiceResult());
        return ResponseUtils.createResponse(statusResponseBody);
    }
}
