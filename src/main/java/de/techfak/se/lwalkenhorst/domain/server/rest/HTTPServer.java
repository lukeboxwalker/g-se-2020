package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.EndRoundRequest;

import de.techfak.se.lwalkenhorst.domain.server.rest.request.GetRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.GseRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.ParticipateRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.PostRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.StatusRequest;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPServer extends NanoHTTPD {
    private static final GetRequest FALLBACK_REQUEST = new GseRequest();

    private final Map<String, PostRequest<?>> postMapping;
    private final Map<String, GetRequest> getMapping;
    private final GameServer gameServer;

    public HTTPServer(int port, final GameServer server) {
        super(port);
        this.gameServer = server;
        this.postMapping = Map.of(
                "/api/participate", new ParticipateRequest(),
                "/api/end-round", new EndRoundRequest());
        this.getMapping = Map.of(
                "", FALLBACK_REQUEST,
                "/api/status", new StatusRequest());
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
        GetRequest getRequest = getMapping.getOrDefault(session.getUri(), FALLBACK_REQUEST);
        return getRequest.handle(gameServer);
    }

    private Response handlePostRequest(final IHTTPSession session) {
        final Map<String, String> data = new HashMap<>();
        try {
            session.parseBody(data);
            if (postMapping.containsKey(session.getUri())) {
                final PostRequest<?> postRequest = postMapping.get(session.getUri());
                return postRequest.handle(gameServer, data.get("postData"));
            } else {
                return FALLBACK_REQUEST.handle(gameServer);
            }
        } catch (IOException ioe) {
            return NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT,
                    "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
        } catch (ResponseException re) {
            return NanoHTTPD.newFixedLengthResponse(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
        }
    }

}
