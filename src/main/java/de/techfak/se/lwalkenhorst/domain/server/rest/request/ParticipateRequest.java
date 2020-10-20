package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.rest.ResponseUtils;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;
import fi.iki.elonen.NanoHTTPD;

import java.util.UUID;

public class ParticipateRequest extends PostRequest<ParticipateRequestBody> {

    private final BoardSerializer serializer = new BoardSerializer();

    @Override
    public Class<ParticipateRequestBody> getBodyClass() {
        return ParticipateRequestBody.class;
    }

    @Override
    public NanoHTTPD.Response handle(GameServer server, ParticipateRequestBody requestBody) {
        final UUID uuid = server.registerPlayer(requestBody.getUsername());
        if (uuid == null) {
            return ResponseUtils.createResponse(new ParticipateResponse(false, "", ""));
        } else {
            System.out.println("Registered player '" + requestBody.getUsername() + "' with uuid: " + uuid);
            final String board = serializer.serialize(server.getBoard());
            return ResponseUtils.createResponse(new ParticipateResponse(true, uuid.toString(), board));
        }
    }
}
