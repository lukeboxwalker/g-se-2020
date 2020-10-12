package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.GetRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.PostRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.Request;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPServer extends NanoHTTPD {
    private final RequestHandler requestHandler;
    private final JSONParser jsonParser = new JSONParser();

    public HTTPServer(int port, final GameServer server) {
        super(port);
        requestHandler = new RequestHandler(jsonParser, server);
    }

    @Override
    public Response serve(final IHTTPSession session) {
        final Method method = session.getMethod();
        if (Method.POST.equals(method)) {
            return handlePostRequest(session);
        } else {
            return handleGetRequest(session);
        }
    }

    private Response handleGetRequest(final IHTTPSession session) {
        GetRequest getRequest = GetRequest.requestForUri(session.getUri());
        return getRequest.handle(requestHandler);
    }

    private Response handlePostRequest(final IHTTPSession session) {
        final Map<String, String> files = new HashMap<>();
        try {
            session.parseBody(files);
            PostRequest postRequest = PostRequest.requestForUri(session.getUri());
            Request request = jsonParser.parseJSON(session.getQueryParameterString(), postRequest.getClazz());
            return request.handle(requestHandler);
        } catch (IOException ioe) {
            return NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return NanoHTTPD.newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        } catch (SerialisationException se) {
            return NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: SerialisationException: " + se.getMessage());
        }
    }

}
