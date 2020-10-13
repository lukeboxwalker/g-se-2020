package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.Board;
import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.MultiplayerGame;
import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;
import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;


public class GseClient {

    public static void main(String... args) {
        try {
            HTTPClient client = new HTTPClient("localhost", 8088);
            BoardSerializer boardSerializer = new BoardSerializer();
            ParticipateResponse response = client.participateRequest(args.length == 1 ? args[0] : "lukas");
            if (response.isSuccess()) {
                Board board = boardSerializer.deSerialize(response.getBoard());
                try (MultiplayerGame game = new MultiplayerGame(board, client)) {
                    Application.start(game);
                }
            } else {
                System.out.println("Cannot play on server. Is the server full?");
            }
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        }
    }
}
