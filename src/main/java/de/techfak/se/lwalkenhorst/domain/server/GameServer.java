package de.techfak.se.lwalkenhorst.domain.server;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.DiceResult;

import java.util.Collection;
import java.util.UUID;

public interface GameServer {

    UUID registerPlayer(final String name);

    boolean updatePoints(String uuid, int points, boolean gameFinished);

    boolean isGameFinished();

    Collection<Player> getPlayers();

    int getRound();

    Board getBoard();

    DiceResult getDiceResult();

}
