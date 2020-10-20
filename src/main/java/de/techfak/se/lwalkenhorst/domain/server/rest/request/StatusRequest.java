package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.rest.RequestHandler;
import fi.iki.elonen.NanoHTTPD;

public class StatusRequest implements GetRequest {

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler) {
        return handler.handleStatusRequest();
    }
}
