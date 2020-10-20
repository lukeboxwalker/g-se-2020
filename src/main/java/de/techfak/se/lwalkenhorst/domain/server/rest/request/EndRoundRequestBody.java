package de.techfak.se.lwalkenhorst.domain.server.rest.request;

public class EndRoundRequestBody implements RequestBody {

    private String uuid;
    private int finalPoints;
    private boolean playerFinished;

    public EndRoundRequestBody() {
    }

    public EndRoundRequestBody(final String uuid, final int finalPoints, final boolean playerFinished) {
        this.uuid = uuid;
        this.finalPoints = finalPoints;
        this.playerFinished = playerFinished;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public int getFinalPoints() {
        return finalPoints;
    }

    public void setFinalPoints(final int finalPoints) {
        this.finalPoints = finalPoints;
    }

    public boolean isPlayerFinished() {
        return playerFinished;
    }

    public void setPlayerFinished(final boolean playerFinished) {
        this.playerFinished = playerFinished;
    }
}
