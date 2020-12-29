package de.techfak.se.lwalkenhorst.multiplayer.server.http.request_body;


import de.techfak.se.lwalkenhorst.multiplayer.game.GameStatus;

/**
 * The request to change the game status.
 */
public class StatusBody {

    private GameStatus status;
    private String name;

    public StatusBody() {
        super();
    }

    public StatusBody(final GameStatus status, final String name) {
        this.status = status;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(final GameStatus status) {
        this.status = status;
    }
}
