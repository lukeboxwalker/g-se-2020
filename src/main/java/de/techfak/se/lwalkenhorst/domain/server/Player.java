package de.techfak.se.lwalkenhorst.domain.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {
    private String username;
    private String uuid;
    private int points;
    private int round;

    public Player() {
    }

    public Player(final String username, final String uuid, final int points) {
        this.username = username;
        this.uuid = uuid;
        this.points = points;
        this.round = 1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getUuid() {
        return uuid;
    }

    @JsonIgnore
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public int getRound() {
        return round;
    }

    public void setRound(final int round) {
        this.round = round;
    }

    @JsonIgnore
    public void enterNextRound() {
        this.round += 1;
    }
}
