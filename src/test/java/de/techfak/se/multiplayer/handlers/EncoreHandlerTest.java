package de.techfak.se.multiplayer.handlers;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EncoreHandlerTest extends AbstractRestTest {

    @Nested
    class Get {

        /**
         * Tests that the response is "Encore".
         */
        @Test
        public void testEncoreResponse() {
            request().get("/").then()
                .contentType(ContentType.TEXT)
                .statusCode(200)
                .body(Matchers.is("Encore"));
        }
    }

    /**
     * Tests that every unimplemented http method is not found.
     */
    @Test
    void testWrongMethod() {
        request().post("/").then().statusCode(404);
        request().delete("/").then().statusCode(404);
        request().accept("/").then().statusCode(404);
        request().put("/").then().statusCode(404);
        request().options("/").then().statusCode(404);
        request().patch("/").then().statusCode(404);
    }
}
