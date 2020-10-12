package de.techfak.se.lwalkenhorst.domain.server.rest;

import de.techfak.se.lwalkenhorst.domain.server.json.JSONParser;
import de.techfak.se.lwalkenhorst.domain.server.json.SerialisationException;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.EndRoundRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.request.ParticipateRequest;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.ParticipateResponse;
import de.techfak.se.lwalkenhorst.domain.server.rest.response.StatusResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPClient {

    private final JSONParser parser;
    private final HttpClient client;
    private final String baseUri;
    private String uuid;

    private static final int OK = 200;

    public HTTPClient(final String serverAddress, final int port) throws NoConnectionException {
        this.client = HttpClient.newHttpClient();
        this.parser = new JSONParser();
        this.baseUri = "http://" + serverAddress + ":" + port;
        final String message = "could not connect to given url " + baseUri;
        try {
            final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUri)).build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != OK || !response.body().equals("GSE NochMal")) {
                throw new NoConnectionException(message);
            }
        } catch (IOException | InterruptedException e) {
            throw new NoConnectionException(message, e);
        }
    }

    public ParticipateResponse participateRequest(String username) {
        try {
            final URI uri = URI.create(baseUri + "/api/participate");
            final String body = parser.toJSON(new ParticipateRequest(username));
            final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
            final HttpRequest request = HttpRequest.newBuilder().uri(uri)
                    .setHeader("content-type", RequestHandler.MIME_JSON).POST(bodyPublisher).build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            ParticipateResponse participateResponse = parser.parseJSON(response.body(), ParticipateResponse.class);
            if (participateResponse.isSuccess()) {
                this.uuid = participateResponse.getUuid();
            }
            return participateResponse;
        } catch (IOException | InterruptedException | SerialisationException e) {
            e.printStackTrace();
            return new ParticipateResponse(false, "", "");
        }
    }

    public void endRoundRequest(int finalPoints, boolean gameFinished) {
        try {
            final URI uri = URI.create(baseUri + "/api/end-round");
            final String body = parser.toJSON(new EndRoundRequest(uuid, finalPoints, gameFinished));
            final HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
            final HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(bodyPublisher).build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | SerialisationException e) {
            e.printStackTrace();
        }
    }

    public StatusResponse statusRequest() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUri + "/api/status")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            return parser.parseJSON(response.body(), StatusResponse.class);
        } catch (IOException | InterruptedException | SerialisationException e) {
            e.printStackTrace();
            return new StatusResponse();
        }
    }
}
