package de.techfak.se.lwalkenhorst.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.lwalkenhorst.exception.NoConnectionException;
import de.techfak.se.lwalkenhorst.exception.RegistrationException;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.response_body.ResponseObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    private static final int OK = 200;

    private final ObjectMapper jsonParser;
    private final HttpClient httpClient;
    private final BaseURI baseUri;

    public Client(final String url) {
        this.httpClient = HttpClient.newHttpClient();
        this.baseUri = new BaseURI(URI.create(url));
        this.jsonParser = new ObjectMapper();
    }

    public void connect() throws NoConnectionException {
        try {
            final HttpRequest request = HttpRequest.newBuilder().uri(baseUri.toURI()).build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK || !"Encore".equals(response.body())) {
                throw new NoConnectionException();
            }
        } catch (IOException | IllegalArgumentException | InterruptedException e) {
            throw new NoConnectionException(e);
        }
    }

    public void register(final String name) throws RegistrationException {
        try {
            final String body = jsonParser.writeValueAsString(new RegisterBody(name));
            final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
            final HttpRequest request = HttpRequest.newBuilder()
                .uri(baseUri.append("/api/game/players"))
                .POST(bodyPublisher)
                .build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK) {
                final ResponseObject object = jsonParser.readValue(response.body(), ResponseObject.class);
                throw new RegistrationException(object.getMessage());
            }
        } catch (IOException | InterruptedException e) {
            throw new RegistrationException(e);
        }
    }
}
