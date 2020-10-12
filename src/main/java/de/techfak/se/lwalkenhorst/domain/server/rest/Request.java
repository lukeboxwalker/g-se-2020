package de.techfak.se.lwalkenhorst.domain.server.rest;

import fi.iki.elonen.NanoHTTPD;

public interface Request {

    NanoHTTPD.Response handle(RequestHandler handler);
}
