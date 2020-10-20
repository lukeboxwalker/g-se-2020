package de.techfak.se.lwalkenhorst.domain.server.rest.request;

public class EndRoundRequestBody implements RequestBody {

    private String uuid;
    private int finalPoints;
    private boolean playerFinished;

    public EndRoundRequestBody() {
    }

    public EndRoundRequestBody(String uuid, int finalPoints, boolean playerFinished) {
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
}
