package de.techfak.se.lwalkenhorst.domain.server;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.Color;
import de.techfak.se.lwalkenhorst.domain.Dice;
import de.techfak.se.lwalkenhorst.domain.DiceResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server implements GameServer {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;

    private final Board board;
    private final int lobbySize;
    private final Map<String, Player> players;
    private final DiceResult diceResult;
    private final Dice<Color> colorDice;
    private final Dice<Integer> numberDice;

    private int round;
    private boolean gameFinished;
    private boolean playerFinished;


    public Server(final int lobbySize, final Board board) {
        this.lobbySize = lobbySize;
        this.players = new HashMap<>();
        this.diceResult = new DiceResult();
        this.colorDice = new Dice<>(Arrays.asList(Color.values()));
        this.numberDice = new Dice<>(Arrays.asList(ONE, TWO, THREE, FOUR, FIVE));
        this.board = board;
        this.round = 0;
        this.gameFinished = false;
        this.playerFinished = false;
        this.rollDice();
    }

    @Override
    public UUID registerPlayer(final String name) {
        if (players.size() < lobbySize) {
            final UUID uuid = UUID.randomUUID();
            final Player player = new Player(name, uuid.toString(), 0);
            players.put(player.getUuid(), player);
            if (players.size() == lobbySize) {
                this.round = 1;
            }
            return uuid;
        }
        return null;
    }

    @Override
    public boolean updatePoints(final String uuid, final int points, final boolean gameFinished) {
        if (players.containsKey(uuid) && players.get(uuid).getRound() == this.round) {
            this.playerFinished = this.playerFinished || gameFinished;
            final Player player = players.get(uuid);
            player.setPoints(points);
            player.enterNextRound();
            if (canEnterNextRound()) {
                this.gameFinished = playerFinished || gameFinished;
                this.rollDice();
                nextRound();
            }
            return true;
        }
        return false;
    }

    public void nextRound() {
        round++;
    }

    @Override
    public boolean isGameFinished() {
        return gameFinished;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players.values();
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public DiceResult getDiceResult() {
        return diceResult;
    }

    private void rollDice() {
        diceResult.setRolledColors(colorDice.rollDice(THREE));
        diceResult.setRolledNumbers(numberDice.rollDice(THREE));
    }

    private boolean canEnterNextRound() {
        for (final Player player : players.values()) {
            if (player.getRound() == this.round) {
                return false;
            }
        }
        return true;
    }
}
