package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.BoardSerializer;
import de.techfak.se.lwalkenhorst.domain.exception.AbstractExitCodeException;
import de.techfak.se.lwalkenhorst.domain.exception.BoardCreationException;
import de.techfak.se.lwalkenhorst.domain.server.GameServer;
import de.techfak.se.lwalkenhorst.domain.server.Server;
import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GseServer {

    private static final String DEFAULT_BOARD_CONFIG = "default.txt";

    public static void main(String... args) {
        try {
            final GameServer game;
            final BoardSerializer boardSerializer = new BoardSerializer();
            if (args.length == 1) {
                game = new Server(2, boardSerializer.deSerialize(new File(args[0])));
            } else {
                try (InputStream inputStream = Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(DEFAULT_BOARD_CONFIG)) {
                    if (inputStream == null) {
                        throw new BoardCreationException("File " + DEFAULT_BOARD_CONFIG + " did not exist!");
                    } else {
                        game = new Server(2, boardSerializer.deSerialize(inputStream));
                    }
                } catch (IOException e) {
                    throw new BoardCreationException(e);
                }
            }
            HTTPServer server = new HTTPServer(8088, game);
            System.out.println("Starting server on http://localhost:8088 ...");
            server.start(0, false);
        } catch (AbstractExitCodeException e) {
            System.err.println(e.getMessage());
            System.exit(e.getExitCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
