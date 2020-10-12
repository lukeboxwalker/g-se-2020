package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.rest.RequestHandler;
import fi.iki.elonen.NanoHTTPD;

public class ParticipateRequest implements Request {
    private String username;

    public ParticipateRequest() {
    }

    public ParticipateRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}