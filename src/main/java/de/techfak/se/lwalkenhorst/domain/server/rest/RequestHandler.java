package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.EndRoundRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.ParticipateRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ResponseBody;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponse;
import fi.iki.elonen.NanoHTTPD;

import java.util.UUID;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public class RequestHandler {

    private static final String MIME_JSON = "application/json";
    private final JSONParser jsonParser;
    private final StatusResponse statusResponse = new StatusResponse();
    private final BoardSerializer boardSerializer = new BoardSerializer();

    public final GameServer server;

    public RequestHandler(final JSONParser jsonParser, final GameServer server) {
        this.jsonParser = jsonParser;
        this.server = server;
    }

    public NanoHTTPD.Response handleStatusRequest() {
        statusResponse.setGameFinished(server.isGameFinished());
        statusResponse.setPlayers(server.getPlayers());
        statusResponse.setRound(server.getRound());
        statusResponse.setDiceResult(server.getDiceResult());
        return createResponse(statusResponse);
    }

    public NanoHTTPD.Response handle(final EndRoundRequest request) {
        boolean success = server.updatePoints(request.getUuid(), request.getFinalPoints(), request.isPlayerFinished());
        return handle(String.valueOf(success));
    }

    public NanoHTTPD.Response handle(final ParticipateRequest request) {
        final UUID uuid = server.registerPlayer(request.getUsername());
        if (uuid == null) {
            return createResponse(new ParticipateResponse(false, "", ""));
        } else {
            final String board = boardSerializer.serialize(server.getBoard());
            return createResponse(new ParticipateResponse(true, uuid.toString(), board));
        }
    }

    public NanoHTTPD.Response handle(final String string) {
        return NanoHTTPD.newFixedLengthResponse(OK, MIME_PLAINTEXT, string);
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
