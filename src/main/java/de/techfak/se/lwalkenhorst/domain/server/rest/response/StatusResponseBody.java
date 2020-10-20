package de.techfak.se.lwalkenhorst.domain.server.rest.response;

import de.techfak.se.lwalkenhorst.domain.DiceResult;
import de.techfak.se.lwalkenhorst.domain.server.Player;

import java.util.Collection;

public class StatusResponseBody implements ResponseBody {
    private boolean gameFinished;
    private int round;
    private Collection<Player> players;
    private DiceResult diceResult;

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(final boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getRound() {
        return round;
    }

    public void setRound(final int round) {
        this.round = round;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(final Collection<Player> players) {
        this.players = players;
    }

    public DiceResult getDiceResult() {
        return diceResult;
    }

    public void setDiceResult(final DiceResult diceResult) {
        this.diceResult = diceResult;
    }
}
