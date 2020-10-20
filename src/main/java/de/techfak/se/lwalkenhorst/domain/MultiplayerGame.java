package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponseBody;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultiplayerGame extends Game implements AutoCloseable {

    private static final int DELAY_MS = 0;
    private static final int PERIOD_MS = 1000;

    private HTTPClient client;
    private Timer startTimer;
    private Timer diceTimer;
    private TimerTask timerTask;

    public MultiplayerGame() {
        super(null);
    }

    public void init(Board board, HTTPClient client) {
        this.setBoard(board);
        this.client = client;
    }

    @Override
    public void start() {
        startTimer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                StatusResponseBody response = client.statusRequest();
                if (response.getRound() == 1) {
                    MultiplayerGame.super.start();
                    startTimer.cancel();
                }
            }
        };
        startTimer.schedule(timerTask, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void pass() {
        client.endRoundRequest(calculatePoints(), false);
        super.pass();
    }

    @Override
    public boolean crossTiles(final List<Position> positions) {
        if (super.crossTiles(positions)) {
            client.endRoundRequest(calculatePoints(), super.isGameFinished());
            return true;
        }
        return false;
    }

    @Override
    protected boolean isGameFinished() {
        return client.statusRequest().isGameFinished();
    }

    @Override
    public void rollDice() {
        this.diceTimer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                StatusResponseBody response = client.statusRequest();
                if (response.getRound() == round) {
                    if (isGameFinished()) {
                        gameObservers.forEach(gameObserver -> gameObserver.onGameEnd(calculatePoints()));
                    }
                    diceResult.setRolledColors(response.getDiceResult().getRolledColors());
                    diceResult.setRolledNumbers(response.getDiceResult().getRolledNumbers());
                    round++;
                    gameObservers.forEach(gameObserver -> gameObserver.onDiceRoll(diceResult));
                    diceTimer.cancel();
                }
            }
        };
        diceTimer.schedule(timerTask, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void close() {
        if (startTimer != null) {
            startTimer.cancel();
        }
        if (diceTimer != null) {
            diceTimer.cancel();
        }
    }
}
