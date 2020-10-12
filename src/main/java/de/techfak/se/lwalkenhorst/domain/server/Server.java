package de.techfak.se.lwalkenhorst.domain.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Server implements GameServer {

    private final int lobbySize;
    private final Map<String, Integer> playerPoints;

    public Server(final int lobbySize) {
        this.lobbySize = lobbySize;
        this.playerPoints = new HashMap<>();
    }

    @Override
    public UUID registerPlayer(String name) {
        if (playerPoints.size() < lobbySize) {
        }
        return null;
    }
}
