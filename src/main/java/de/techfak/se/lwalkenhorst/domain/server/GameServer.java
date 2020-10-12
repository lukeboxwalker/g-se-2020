package de.techfak.se.lwalkenhorst.domain.server;

import java.util.UUID;

public interface GameServer {

    UUID registerPlayer(final String name);
}
