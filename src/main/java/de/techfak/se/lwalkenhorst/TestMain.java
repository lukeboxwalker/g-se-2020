package de.techfak.se.lwalkenhorst;

import de.techfak.se.lwalkenhorst.domain.server.rest.HTTPClient;
import de.techfak.se.lwalkenhorst.domain.server.rest.NoConnectionException;

public class TestMain {

    public static void main(String... args) {
        try {
            HTTPClient client = new HTTPClient("localhost", 8088);
            client.participateRequest("lukas");
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
}
