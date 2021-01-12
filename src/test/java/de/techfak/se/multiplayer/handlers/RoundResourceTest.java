package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.request_body.EndRoundBody;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.RoundResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class RoundResourceTest extends AbstractRestTest {

    private static final String ROUND_URI = "/api/game/round";
    private static final String PLAYER_URI = "/api/game/players";
    private static final String STATUS_URI = "/api/game/status";
    private static final String PLAYER1_NAME = "Test-Player1";
    private static final String PLAYER2_NAME = "Test-Player2";
    private static final String PARAM_NAME = "name";
    private static final String EMPTY_STRING = "";

    @Nested
    class Get {

        /**
         * Tests that player cant query the round when no name was given to the server.
         */
        @Test
        void testGetRoundNoName() {
            request().get(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player cant query the round when name is empty.
         */
        @Test
        void testGetRoundEmptyName() {
            request().queryParam(PARAM_NAME, EMPTY_STRING).get(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player cant query the round when name is not registered.
         */
        @Test
        void testGetRoundNoRegisteredName() {
            request().queryParam(PARAM_NAME, PLAYER1_NAME).get(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests that player cant query the round when game is not started.
         */
        @Test
        void testGetRoundNotStarted() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().queryParam(PARAM_NAME, PLAYER1_NAME).get(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that registered player can query the round when game is started.
         */
        @Test
        void testGetRound() {
            //register player
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);

            //start the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //requesting current round
            final Response response = request().queryParam(PARAM_NAME, PLAYER1_NAME).get(ROUND_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            final RoundResponse roundResponse = response.as(RoundResponse.class);
            Assertions.assertTrue(roundResponse.isSuccess());
            Assertions.assertEquals(1, roundResponse.getRound());
        }
    }

    @Nested
    class Post {

        /**
         * Tests that player cant end their round when no body is given.
         */
        @Test
        void testPostEndRoundNoBody() {
            //register player
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);

            //start the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //send points to end the round but no body is given
            request().post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player cant end their round when name is null.
         */
        @Test
        void testPostEndRoundNameNull() {
            //register player
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);

            //start the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //send points to end the round but player name is null
            request().body(new EndRoundBody(null, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player can end their round.
         */
        @Test
        void testPostEndRound() {
            //register player
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);

            //start the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //send points to end round 1
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            Assertions.assertEquals(getBaseGame().getPlayers().get(0).getPoints().getValue(), 42);
            Assertions.assertEquals(GameStatus.RUNNING, getBaseGame().getGameStatus());
            Assertions.assertEquals(2, getBaseGame().getRound().getRound());
        }

        /**
         * Tests that server game waits with next round until every player has finished their round.
         */
        @Test
        void testPostEndRoundSecondPlayer() {
            //register players
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI);

            //player 1 starts the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //player 1 finishes round 1 but game should stay in round 1 until each player finishes the round
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            Assertions.assertEquals(GameStatus.RUNNING, getBaseGame().getGameStatus());
            Assertions.assertEquals(1, getBaseGame().getRound().getRound());
        }

        /**
         * Tests that player cant end their round twice. Player needs to wait for next round.
         */
        @Test
        void testPostEndRoundTwice() {
            //register players
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI);

            //player 1 starts the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //player 1 finishes round 1
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            //player 1 tries to finish round 1 again
            request().body(new EndRoundBody(PLAYER1_NAME, 0)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);

            Assertions.assertEquals(1, getBaseGame().getRound().getRound());
        }

        /**
         * Tests that player cant end a next round when the game is finished
         */
        @Test
        void testPostEndRoundFinishGame() {
            //register players
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI);

            //player 1 starts the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //player 1 finishes round 1
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI);
            Assertions.assertEquals(getBaseGame().getGameStatus(), GameStatus.RUNNING);

            //player 2 signals that he finished the game and sends his points for round 1
            request().body(new StatusBody(GameStatus.FINISHED, PLAYER2_NAME)).post(STATUS_URI);
            request().body(new EndRoundBody(PLAYER2_NAME, 42)).post(ROUND_URI);
            Assertions.assertEquals(GameStatus.FINISHED, getBaseGame().getGameStatus());

            //player 1 tries to finishes the next round although the game is finished
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);
        }

        /**
         * Tests that every player can finish the current round when one player finishes the game
         */
        @Test
        void testPostEndRoundAfterFinishGame() {
            //register players
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI);

            //player 1 starts the game
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            //player 2 signals that he finished the game and sends his points for round 1
            request().body(new StatusBody(GameStatus.FINISHED, PLAYER2_NAME)).post(STATUS_URI);
            request().body(new EndRoundBody(PLAYER2_NAME, 42)).post(ROUND_URI);
            Assertions.assertEquals(GameStatus.FINISHED, getBaseGame().getGameStatus());

            //player 1 finishes round 1
            request().body(new EndRoundBody(PLAYER1_NAME, 42)).post(ROUND_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);
        }
    }

    /**
     * Tests that every unimplemented http method is not found.
     */
    @Test
    void testWrongMethod() {
        request().delete(ROUND_URI).then().statusCode(404);
        request().accept(ROUND_URI).then().statusCode(404);
        request().put(ROUND_URI).then().statusCode(404);
        request().options(ROUND_URI).then().statusCode(404);
        request().patch(ROUND_URI).then().statusCode(404);
    }
}
