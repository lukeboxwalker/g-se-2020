package de.techfak.se.lwalkenhorst.domain.server.rest.request;

import de.techfak.se.lwalkenhorst.domain.server.rest.RequestHandler;
import fi.iki.elonen.NanoHTTPD;

public class EndRoundRequest implements Request {

    private String uuid;
    private int finalPoints;
    private boolean playerFinished;

    public EndRoundRequest(String uuid, int finalPoints, boolean playerFinished) {
        this.uuid = uuid;
        this.finalPoints = finalPoints;
        this.playerFinished = playerFinished;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getFinalPoints() {
        return finalPoints;
    }

    public void setFinalPoints(int finalPoints) {
        this.finalPoints = finalPoints;
    }

    public boolean isPlayerFinished() {
        return playerFinished;
    }

    public void setPlayerFinished(boolean playerFinished) {
        this.playerFinished = playerFinished;
    }

    @Override
    public NanoHTTPD.Response handle(RequestHandler handler) {
        return handler.handle(this);
    }
}
