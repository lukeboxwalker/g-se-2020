package de.techfak.se.lwalkenhorst.domain.server.rest;


import fi.iki.elonen.NanoHTTPD;

public class GseRequest implements Request {

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler) {
        return handler.handle("GSE-Nochmal!");
    }
}
