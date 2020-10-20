package de.techfak.se.lwalkenhorst.domain.server.rest.request;

public class ParticipateRequestBody implements RequestBody {
    private String username;

    public ParticipateRequestBody() {
    }

    public ParticipateRequestBody(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
