package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.MultiplayerGame;
import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;
import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;


public class GseClient {

    public static void main(String... args) {
        try {
            HTTPClient client = new HTTPClient("localhost", 8088);
            BoardSerializer boardSerializer = new BoardSerializer();
            Board board = boardSerializer.deSerialize(client.participateRequest("lukas").getBoard());
            try (MultiplayerGame game = new MultiplayerGame(board, client)) {
                Application.start(game);
            }

        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }
}
