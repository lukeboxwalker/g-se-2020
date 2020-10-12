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
        this.numberDice = new Dice<>(Arrays.asList(1, 2, 3, 4, 5));
        this.board = board;
        this.round = 1;
        this.gameFinished = false;
        this.playerFinished = false;
        rollDice();
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
            final Player player = players.get(uuid);
            player.setPoints(points);
            player.enterNextRound();
            if (canEnterNextRound()) {
                if (playerFinished) {
                    this.gameFinished = true;
                }
                this.rollDice();
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

    @Override
    public DiceResult getDiceResult() {
        return diceResult;
    }

    private void rollDice() {
        diceResult.setRolledColors(colorDice.rollDice(3));
        diceResult.setRolledNumbers(numberDice.rollDice(3));
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
