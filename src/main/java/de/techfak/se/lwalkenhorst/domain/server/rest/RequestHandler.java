package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.EndRoundRequestBody;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.ParticipateRequestBody;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ResponseBody;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponse;
import fi.iki.elonen.NanoHTTPD;

import java.util.UUID;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public class RequestHandler {

    public static final String MIME_JSON = "application/json";

    private final JSONParser jsonParser;
    private final StatusResponse statusResponse = new StatusResponse();
    private final BoardSerializer boardSerializer = new BoardSerializer();

    public final GameServer server;

    public RequestHandler(final GameServer server) {
        this.jsonParser =  new JSONParser();;
        this.server = server;
    }

    public NanoHTTPD.Response handleStatusRequest() {
        statusResponse.setGameFinished(server.isGameFinished());
        statusResponse.setPlayers(server.getPlayers());
        statusResponse.setRound(server.getRound());
        statusResponse.setDiceResult(server.getDiceResult());
        return createResponse(statusResponse);
    }

    public NanoHTTPD.Response handle(final EndRoundRequestBody request) {
        boolean success = server.updatePoints(request.getUuid(), request.getFinalPoints(), request.isPlayerFinished());
        if (success) {
            System.out.println("Player with uuid: " + request.getUuid() + " finished there round.");
        }
        return handle(String.valueOf(success));
    }

    public NanoHTTPD.Response handle(final ParticipateRequestBody requestBody) {
        final UUID uuid = server.registerPlayer(requestBody.getUsername());
        if (uuid == null) {
            return createResponse(new ParticipateResponse(false, "", ""));
        } else {
            System.out.println("Registered player '" + requestBody.getUsername() + "' with uuid: " + uuid);
            final String board = boardSerializer.serialize(server.getBoard());
            return createResponse(new ParticipateResponse(true, uuid.toString(), board));
        }
    }

    public NanoHTTPD.Response handle(final String plainText) {
        return NanoHTTPD.newFixedLengthResponse(OK, MIME_PLAINTEXT, plainText);
    }

    private NanoHTTPD.Response createResponse(final ResponseBody responseBody) {
        try {
            return NanoHTTPD.newFixedLengthResponse(OK, MIME_JSON, jsonParser.toJSON(responseBody));
        } catch (SerialisationException se) {
            return NanoHTTPD.newFixedLengthResponse(INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: SerialisationException: " + se.getMessage());
        }
    }
}
