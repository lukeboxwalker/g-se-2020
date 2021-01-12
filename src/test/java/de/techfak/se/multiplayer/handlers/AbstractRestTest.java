package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.BoardParser;
import de.techfak.se.multiplayer.game.BoardParserImpl;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.server.Server;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

import static io.restassured.RestAssured.given;

class AbstractRestTest {

    private final BoardParser parser = new BoardParserImpl();

    private Server server;
    private int port = 0;

    @BeforeEach
    public void setUp() throws Exception {
        final File file = new File(getClass().getResource("/boards/boardNormal.txt").toURI());
        final SynchronizedGame game = new SynchronizedGame(new BaseGameImpl(parser.parse(file)));
        server = new Server(game);
        server.start(0);
        port = server.getListeningPort();
    }

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    public RequestSpecification request() {
        return given().port(port).when();
    }

    public BaseGame getBaseGame() {
        return server.getGame();
    }

}
