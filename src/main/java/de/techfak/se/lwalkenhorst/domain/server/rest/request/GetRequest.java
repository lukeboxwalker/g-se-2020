package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import fi.iki.elonen.NanoHTTPD;

public interface GetRequest {

    NanoHTTPD.Response handle(GameServer server);
}
