package de.techfak.se.lwalkenhorst.domain.server.rest;

import fi.iki.elonen.NanoHTTPD;

import java.util.HashMap;
import java.util.Map;

public class GetRequest {

    public static final GetRequest GSE_REQUEST = new GetRequest("", new GseRequest());

    private static final Map<String, GetRequest> REQUEST_MAP = new HashMap<>();
    private final String uri;
    private final Request request;

    static {
        REQUEST_MAP.put(GSE_REQUEST.getUri(), GSE_REQUEST);
    }

    GetRequest(final String uri, final Request request) {
        this.uri = uri;
        this.request = request;
    }

    public String getUri() {
        return uri;
    }

    public NanoHTTPD.Response handle(final RequestHandler handler) {
        return request.handle(handler);
    }

    public static GetRequest requestForUri(final String uri) {
        return REQUEST_MAP.getOrDefault(uri, GSE_REQUEST);
    }
}