package de.techfak.se.lwalkenhorst.domain.server;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {
    private String username;
    private String uuid;
    private int points;
    private int round;

    public Player() {
    }

    public Player(final String username, final String uuid, int points) {
        this.username = username;
        this.uuid = uuid;
        this.points = points;
        this.round = 1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getUuid() {
        return uuid;
    }

    @JsonIgnore
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @JsonIgnore
    public void enterNextRound() {
        this.round += 1;
    }
}
