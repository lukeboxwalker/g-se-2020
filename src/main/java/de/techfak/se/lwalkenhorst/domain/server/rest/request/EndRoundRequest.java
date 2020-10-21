package de.techfak.se.lwalkenhorst.domain.server.rest.request;


import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.rest.ResponseUtils;
import fi.iki.elonen.NanoHTTPD;

public class EndRoundRequest extends AbstractPostRequest<EndRoundRequestBody> {
    @Override
    public Class<EndRoundRequestBody> getBodyClass() {
        return EndRoundRequestBody.class;
    }

    @Override
    public NanoHTTPD.Response handle(final GameServer server, final EndRoundRequestBody requestBody) {
        final String uuid = requestBody.getUuid();
        final int points = requestBody.getFinalPoints();
        final boolean playerFinished = requestBody.isPlayerFinished();
        final boolean success = server.updatePoints(uuid, points, playerFinished);
        if (success) {
            System.out.println("Player with uuid: " + uuid + " finished the round.");
        }
        return ResponseUtils.createResponse(String.valueOf(success));
    }
}
