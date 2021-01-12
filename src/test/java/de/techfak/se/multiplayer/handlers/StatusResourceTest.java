package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class StatusResourceTest extends AbstractRestTest {

    private static final String PLAYER_URI = "/api/game/players";
    private static final String STATUS_URI = "/api/game/status";
    private static final String PLAYER_NAME = "Test-Player";
    private static final String PARAM_NAME = "name";
    private static final String EMPTY_STRING = "";

    @Nested
    class Get {

        /**
         * Tests if player cant query status when no name was given to the server.
         */
        @Test
        void testGetStatusNoName() {
            request().get(STATUS_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query status when name is empty.
         */
        @Test
        void testGetStatusEmptyName() {
            request().queryParam(PARAM_NAME, EMPTY_STRING).get(STATUS_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query status when name is not registered.
         */
        @Test
        void testGetStatusNoRegisteredName() {
            request().queryParam(PARAM_NAME, PLAYER_NAME).get(STATUS_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests that player can query status when given name is registered as a player.
         */
        @Test
        void testGetStatus() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
            final Response response = request().queryParam(PARAM_NAME, PLAYER_NAME).get(STATUS_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            final StatusResponse responseObject = response.as(StatusResponse.class);
            Assertions.assertNotNull(responseObject);
            Assertions.assertTrue(responseObject.isSuccess());
            Assertions.assertEquals(GameStatus.NOT_STARTED, responseObject.getStatus());
        }
    }

    @Nested
    class Post {

        /**
         * Tests that player cant end their round when no body is given.
         */
        @Test
        void testPostEndRoundNoBody() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);

            //start the game but no body is given
            request().post(STATUS_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests that player cant update status when status is null
         */
        @Test
        void testPostStatusNoStatus() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(null, PLAYER_NAME)).post(STATUS_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(409);
        }

        @Nested
        class AdvanceFromNotStarted {
            /**
             * Tests status change from NOT_STARTED to RUNNING.
             */
            @Test
            void testUpdateFromNotStartedToRunning() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(200);

                Assertions.assertEquals(GameStatus.RUNNING, getBaseGame().getGameStatus());
            }

            /**
             * Tests status change from NOT_STARTED to FINISHED.
             */
            @Test
            void testUpdateFromNotStartedToFinished() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.FINISHED, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(409);

                Assertions.assertEquals(GameStatus.NOT_STARTED, getBaseGame().getGameStatus());
            }
        }

        @Nested
        class AdvanceFromRunning {

            /**
             * Tests status change from RUNNING to FINISHED.
             */
            @Test
            void testUpdateFromRunningToFinished() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.FINISHED, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(200);

                Assertions.assertEquals(GameStatus.FINISHED, getBaseGame().getGameStatus());
            }

            /**
             * Tests status change from RUNNING to NOT_STARTED.
             */
            @Test
            public void testUpdateToNotStartedFromRunning() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.NOT_STARTED, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(409);

                Assertions.assertEquals(GameStatus.RUNNING, getBaseGame().getGameStatus());
            }
        }

        @Nested
        class AdvanceFromFinished {

            /**
             * Tests status change from FINISHED to RUNNING.
             */
            @Test
            public void testUpdateFromFinishedToRunning() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.FINISHED, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(409);

                Assertions.assertEquals(GameStatus.FINISHED, getBaseGame().getGameStatus());
            }

            /**
             * Tests status change from FINISHED to NOT_STARTED.
             */
            @Test
            public void testUpdateFromFinishedToNotStarted() {
                request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
                request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.FINISHED, PLAYER_NAME)).post(STATUS_URI);
                request().body(new StatusBody(GameStatus.NOT_STARTED, PLAYER_NAME)).post(STATUS_URI).then()
                    .contentType(ContentType.JSON)
                    .statusCode(409);

                Assertions.assertEquals(GameStatus.FINISHED, getBaseGame().getGameStatus());
            }
        }
    }


    /**
     * Tests that every unimplemented http method is a not allowed request.
     */
    @Test
    public void testWrongMethod() {
        request().delete(STATUS_URI).then().statusCode(404);
        request().accept(STATUS_URI).then().statusCode(404);
        request().put(STATUS_URI).then().statusCode(404);
        request().options(STATUS_URI).then().statusCode(404);
        request().patch(STATUS_URI).then().statusCode(404);
    }
}
