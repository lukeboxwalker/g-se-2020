package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public class RequestHandler {

    private static final String MIME_JSON = "application/json";
    private final JSONParser jsonParser = new JSONParser();

    public final GameServer server;

    public RequestHandler(final GameServer server) {
        this.server = server;
    }

    public NanoHTTPD.Response handle(final ParticipateRequest request) {
        return createResponse(new ParticipateResponse(true, "test", "sdasd"));
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
