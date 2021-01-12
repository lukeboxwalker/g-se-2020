package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.response_body.BoardResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardResourceTest extends AbstractRestTest {

    private static final String BOARD_URI = "/api/game/board";
    private static final String PLAYER_URI = "/api/game/players";
    private static final String PLAYER_NAME = "Test-Player";
    private static final String PARAM_NAME = "name";
    private static final String EMPTY_STRING = "";

    @Nested
    class Get {
        /**
         * Tests if player cant query the board when no name was given to the server.
         */
        @Test
        void testGetBoardNoName() {
            request().get(BOARD_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query the board when name is empty.
         */
        @Test
        void testGetBoardEmptyName() {
            request().queryParam(PARAM_NAME, EMPTY_STRING).get(BOARD_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query the board when name is not registered.
         */
        @Test
        void testGetBoardNoRegisteredName() {
            request().queryParam(PARAM_NAME, PLAYER_NAME).get(BOARD_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests if registered player can query the board.
         */
        @Test
        void testGetBoard() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
            final Response response = request().queryParam(PARAM_NAME, PLAYER_NAME).get(BOARD_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            final BoardResponse boardResponse = response.as(BoardResponse.class);
            Assertions.assertTrue(boardResponse.isSuccess());
        }
    }

    /**
     * Tests that every unimplemented http method is not found.
     */
    @Test
    void testWrongMethod() {
        request().post(BOARD_URI).then().statusCode(404);
        request().delete(BOARD_URI).then().statusCode(404);
        request().accept(BOARD_URI).then().statusCode(404);
        request().put(BOARD_URI).then().statusCode(404);
        request().options(BOARD_URI).then().statusCode(404);
        request().patch(BOARD_URI).then().statusCode(404);
    }
}
