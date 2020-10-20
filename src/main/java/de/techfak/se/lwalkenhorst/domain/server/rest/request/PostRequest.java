package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;

public abstract class PostRequest<T extends RequestBody> {

    private static final JSONParser JSON_PARSER = new JSONParser();

    public abstract Class<T> getBodyClass();

    public abstract NanoHTTPD.Response handle(GameServer server, T requestBody);

    public NanoHTTPD.Response handle(GameServer server, String data) {
        try {
            final T requestBody = JSON_PARSER.parseJSON(data, getBodyClass());
            return this.handle(server, requestBody);
        } catch (SerialisationException se) {
            se.printStackTrace();
            return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: SerialisationException: " + se.getMessage());
        }
    }
}
