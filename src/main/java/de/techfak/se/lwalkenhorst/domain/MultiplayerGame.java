package de.techfak.se.lwalkenhorst.domain;

import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponse;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultiplayerGame extends Game implements AutoCloseable {

    private static final int DELAY_MS = 0;
    private static final int PERIOD_MS = 100;

    private final HTTPClient client;
    private final Timer timer = new Timer();
    private TimerTask timerTask;

    public MultiplayerGame(final Board board, final HTTPClient client) {
        super(board);
        this.client = client;
    }

    @Override
    public void pass() {
        client.endRoundRequest(calculatePoints(), false);
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
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                StatusResponse response = client.statusRequest();
                if (response.getRound() == round) {
                    diceResult.setRolledColors(response.getDiceResult().getRolledColors());
                    diceResult.setRolledNumbers(response.getDiceResult().getRolledNumbers());
                    round++;
                    gameObservers.forEach(gameObserver -> gameObserver.onDiceRoll(diceResult));
                    timerTask.cancel();
                }
            }
        };
        timer.schedule(timerTask, DELAY_MS, PERIOD_MS);
    }

    @Override
    public void close() {
        timer.cancel();
    }
}
