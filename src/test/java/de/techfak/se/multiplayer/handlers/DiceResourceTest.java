package de.techfak.se.multiplayer.handlers;

import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.DiceResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DiceResourceTest extends AbstractRestTest {

    private static final String DICE_URI = "/api/game/dice";
    private static final String PLAYER_URI = "/api/game/players";
    private static final String STATUS_URI = "/api/game/status";
    private static final String PLAYER_NAME = "Test-Player";
    private static final String PARAM_NAME = "name";
    private static final String EMPTY_STRING = "";

    @Nested
    class Get {

        /**
         * Tests if player cant query the dice result when no name was given to the server.
         */
        @Test
        void testGetDiceNoName() {
            request().get(DICE_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query the dice result when name is empty.
         */
        @Test
        void testGetDiceEmptyName() {
            request().queryParam(PARAM_NAME, EMPTY_STRING).get(DICE_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player cant query the dice result when name is not registered.
         */
        @Test
        void testGetDiceNoRegisteredName() {
            request().queryParam(PARAM_NAME, PLAYER_NAME).get(DICE_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(403);
        }

        /**
         * Tests if player cant query the dice result when game is not started.
         */
        @Test
        void testGetDiceWhenNotStarted() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
            request().queryParam(PARAM_NAME, PLAYER_NAME).get(DICE_URI).then()
                .contentType(ContentType.JSON)
                .statusCode(400);
        }

        /**
         * Tests if player can query the dice result.
         */
        @Test
        void testGetDice() {
            request().body(new RegisterBody(PLAYER_NAME)).post(PLAYER_URI);
            request().body(new StatusBody(GameStatus.RUNNING, PLAYER_NAME)).post(STATUS_URI);
            final Response response = request().queryParam(PARAM_NAME, PLAYER_NAME).get(DICE_URI);
            response.then()
                .contentType(ContentType.JSON)
                .statusCode(200);

            final DiceResponse diceResponse = response.as(DiceResponse.class);
            Assertions.assertTrue(diceResponse.isSuccess());
            Assertions.assertEquals(3, diceResponse.getColors().size());
            Assertions.assertEquals(3, diceResponse.getNumbers().size());
        }
    }

    /**
     * Tests that every unimplemented http method is not found.
     */
    @Test
    void testWrongMethod() {
        request().post(DICE_URI).then().statusCode(404);
        request().delete(DICE_URI).then().statusCode(404);
        request().accept(DICE_URI).then().statusCode(404);
        request().put(DICE_URI).then().statusCode(404);
        request().options(DICE_URI).then().statusCode(404);
        request().patch(DICE_URI).then().statusCode(404);
    }
}
