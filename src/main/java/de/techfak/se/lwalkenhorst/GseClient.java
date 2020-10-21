package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.MultiplayerGame;

public final class GseClient {

    private GseClient() {
    }

    public static void main(final String... args) {
        try (MultiplayerGame multiplayerGame = new MultiplayerGame()) {
            ClientApplication.start(multiplayerGame);
        }
    }
}
