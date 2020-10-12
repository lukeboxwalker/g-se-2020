package de.techfak.se.lwalkenhorst.domain.server.rest.response;

import de.techfak.se.lwalkenhorst.domain.server.Player;

import java.util.Collection;
import java.util.List;

public class StatusResponse implements ResponseBody {
    private boolean gameFinished;
    private int round;
    private Collection<Player> players;

    public StatusResponse() {
    }

    public StatusResponse(boolean gameFinished, int round, List<Player> players) {
        this.gameFinished = gameFinished;
        this.round = round;
        this.players = players;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }
}
