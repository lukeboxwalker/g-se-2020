package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.PlayerListResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayerResourceTest extends AbstractRestTest {

    private static final String PLAYER_URI = "/api/game/players";
    private static final String STATUS_URI = "/api/game/status";
    private static final String PLAYER1_NAME = "Test-Player1";
    private static final String PLAYER2_NAME = "Test-Player2";
    private static final String PARAM_NAME = "name";
    private static final String EMPTY_STRING = "";

    @Nested
    class Get {

        /**
         * Tests that player list cant be queried when no name was given to the server.
         */
        @Test
        public void testListPlayersNoName() {
            request().get(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player list cant be queried when given name is empty.
         */
        @Test
        public void testListPlayersEmptyName() {
            request().queryParam(PARAM_NAME, EMPTY_STRING).get(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player list cant be queried when given name is not registered as a player.
         */
        @Test
        public void testListPlayersWhenNotRegistered() {
            request().queryParam(PARAM_NAME, PLAYER1_NAME).get(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests that player list can be queried when given name is registered as a player.
         */
        @Test
        public void testListPlayersWhenRegistered() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            final Response response = request().queryParam(PARAM_NAME, PLAYER1_NAME).get(PLAYER_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            final PlayerListResponse playerListResponse = response.as(PlayerListResponse.class);
            Assertions.assertNotNull(playerListResponse);
            Assertions.assertTrue(playerListResponse.isSuccess());
            Assertions.assertEquals(1, playerListResponse.getPlayers().size());
            Assertions.assertEquals(PLAYER1_NAME, playerListResponse.getPlayers().get(0).getName());
        }
    }

    @Nested
    class Post {

        /**
         * Tests if player cant register when no name was given to the server.
         */
        @Test
        public void testRegisterPlayerNoBody() {
            request().post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant register when name is null.
         */
        @Test
        public void testRegisterPlayerNameNull() {
            request().body(new RegisterBody(null)).post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant register when name is empty.
         */
        @Test
        public void testRegisterPlayerEmptyName() {
            request().body(new RegisterBody(EMPTY_STRING)).post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player can register to the server.
         */
        @Test
        public void testRegisterPlayer() {
            final Response response = request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            Assertions.assertEquals(getBaseGame().getPlayers().get(0).getName().getName(), PLAYER1_NAME);
        }

        /**
         * Tests if player cant register to the server when game is running.
         */
        @Test
        public void testRegisterPlayerWhenRunning() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);

            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);
        }

        /**
         * Tests if player cant register to the server when game is finished.
         */
        @Test
        public void testRegisterPlayerWhenFinished() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);
            request().body(new StatusBody(GameStatus.FINISHED, PLAYER1_NAME)).post(STATUS_URI);

            request().body(new RegisterBody(PLAYER2_NAME)).post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);
        }

        /**
         * Tests that a player name cant be registered twice.
         */
        @Test
        public void testRegisterSamePlayerTwice() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);

            Assertions.assertEquals(1, getBaseGame().getPlayers().size());
            Assertions.assertEquals(PLAYER1_NAME, getBaseGame().getPlayers().get(0).getName().getName());
        }
    }

    @Nested
    class Delete {

        /**
         * Tests that player cant be deleted when not registered.
         */
        @Test
        public void testDeleteNotRegisteredPlayer() {
            request().queryParam(PARAM_NAME, PLAYER1_NAME).delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests that player can be deleted when game is not started.
         */
        @Test
        public void testDeletePlayerWhenGameNotStarted() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().queryParam(PARAM_NAME, PLAYER1_NAME).delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);
        }

        /**
         * Tests that player can be deleted when game is running.
         */
        @Test
        public void testDeletePlayerWhenGameRunning() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);
            request().queryParams(PARAM_NAME, PLAYER1_NAME).delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(200);
        }

        /**
         * Tests that player cant be deleted when game is finished.
         */
        @Test
        public void testDeletePlayerWhenGameFinished() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER1_NAME)).post(STATUS_URI);
            request().body(new StatusBody(GameStatus.FINISHED, PLAYER1_NAME)).post(STATUS_URI);

            request().queryParams(PARAM_NAME, PLAYER1_NAME).delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);
        }

        /**
         * Tests that player cant be deleted when no name was given to the server.
         */
        @Test
        public void testDeletePlayerNoName() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player cant be deleted when name was empty.
         */
        @Test
        public void testDeletePlayerEmptyName() {
            request().body(new RegisterBody(PLAYER1_NAME)).post(PLAYER_URI);
            request().queryParams(PARAM_NAME, EMPTY_STRING).delete(PLAYER_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }
    }

    /**
     * Tests that every unimplemented http method is a not allowed request.
     */
    @Test
    public void testWrongMethod() {
        request().accept(PLAYER_URI).then().statusCode(404);
        request().put(PLAYER_URI).then().statusCode(404);
        request().options(PLAYER_URI).then().statusCode(404);
        request().patch(PLAYER_URI).then().statusCode(404);
    }
}
