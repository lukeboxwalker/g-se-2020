package de.techfak.se.lwalkenhorst.domain.server.rest;

import java.util.HashMap;
import java.util.Map;

public class PostRequest {

    public static final PostRequest PARTICIPATE_REQUEST = new PostRequest("/api/participate", ParticipateRequest.class);

    private static final Map<String, PostRequest> REQUEST_MAP = new HashMap<>();
    private final String uri;
    private final Class<? extends Request> clazz;

    static {
        REQUEST_MAP.put(PARTICIPATE_REQUEST.getUri(), PARTICIPATE_REQUEST);
    }

    PostRequest(final String uri, final Class<? extends Request> clazz) {
        this.uri = uri;
        this.clazz = clazz;
    }

    public String getUri() {
        return uri;
    }

    public Class<? extends Request> getClazz() {
        return clazz;
    }

    public static PostRequest requestForUri(final String uri) {
        return REQUEST_MAP.get(uri);
    }
}
