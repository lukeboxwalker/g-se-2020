package de.techfak.se.lwalkenhorst.domain.server.rest.request;


import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.rest.ResponseUtils;
import fi.iki.elonen.NanoHTTPD;

public class GseRequest implements GetRequest {

    @Override
    public NanoHTTPD.Response handle(GameServer server) {
        return ResponseUtils.createResponse("GSE NochMal");
    }
}
