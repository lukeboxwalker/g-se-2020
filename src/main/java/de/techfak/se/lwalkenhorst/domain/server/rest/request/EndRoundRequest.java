package de.techfak.se.lwalkenhorst.domain.server.rest.request;


import de.techfak.se.lwalkenhorst.domain.server.rest.RequestHandler;
import fi.iki.elonen.NanoHTTPD;

public class EndRoundRequest extends PostRequest<EndRoundRequestBody> {
    @Override
    public Class<EndRoundRequestBody> getBodyClass() {
        return EndRoundRequestBody.class;
    }

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler, EndRoundRequestBody requestBody) {
        return handler.handle(requestBody);
    }
}
