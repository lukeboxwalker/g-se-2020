package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.rest.RequestHandler;
import fi.iki.elonen.NanoHTTPD;

public class ParticipateRequest extends PostRequest<ParticipateRequestBody> {

    @Override
    public Class<ParticipateRequestBody> getBodyClass() {
        return ParticipateRequestBody.class;
    }

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler, ParticipateRequestBody requestBody) {
        return handler.handle(requestBody);
    }
}
