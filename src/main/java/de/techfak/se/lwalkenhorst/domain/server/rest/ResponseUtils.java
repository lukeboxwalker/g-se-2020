package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ResponseBody;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public final class ResponseUtils {

    public static final String MIME_JSON = "application/json";

    private static final JSONParser JSON_PARSER = new JSONParser();

    public static NanoHTTPD.Response createResponse(final ResponseBody responseBody) {
        try {
            return NanoHTTPD.newFixedLengthResponse(OK, MIME_JSON, JSON_PARSER.toJSON(responseBody));
        } catch (SerialisationException se) {
            return NanoHTTPD.newFixedLengthResponse(INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: SerialisationException: " + se.getMessage());
        }
    }

    public static NanoHTTPD.Response createResponse(final String plainText) {
        return NanoHTTPD.newFixedLengthResponse(OK, MIME_PLAINTEXT, plainText);
    }
}
