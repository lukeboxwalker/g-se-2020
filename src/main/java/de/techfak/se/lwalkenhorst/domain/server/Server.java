package de.techfak.se.lwalkenhorst.domain.server;

import de.techfak.se.lwalkenhorst.domain.Board;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server implements GameServer {

    private final Board board;
    private final int lobbySize;
    private final Map<String, Player> players;

    private int round;
    private boolean gameFinished;
    private boolean playerFinished;

    public Server(final int lobbySize, final Board board) {
        this.lobbySize = lobbySize;
        this.players = new HashMap<>();
        this.board = board;
        this.round = 1;
        this.gameFinished = false;
        this.playerFinished = false;
    }

    @Override
    public UUID registerPlayer(String name) {
        if (players.size() < lobbySize) {
            UUID uuid = UUID.randomUUID();
            Player player = new Player(name, UUID.randomUUID().toString(), 0);
            players.put(player.getUuid(), player);
            return uuid;
        }
        return null;
    }

    @Override
    public boolean updatePoints(String uuid, int points, boolean gameFinished) {
        if (players.containsKey(uuid) && players.get(uuid).getRound() == this.round) {
            this.playerFinished = gameFinished;
            final Player player =  players.get(uuid);
            player.setPoints(points);
            player.enterNextRound();
            if (canEnterNextRound()) {
                if (playerFinished) {
                    this.gameFinished = true;
                }
                this.round++;
            }
            return true;
        }
        return false;
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

    private boolean canEnterNextRound() {
        for (Player player : players.values()) {
            if (player.getRound() == this.round) {
                return false;
            }
        }
        return true;
    }
}
