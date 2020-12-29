package de.techfak.se.lwalkenhorst.multiplayer.server.http.response_body;


import de.techfak.se.lwalkenhorst.multiplayer.game.Board;

/**
 * The response containing a board configuration.
 */
public class BoardResponse extends ResponseObject {

    private String board;

    public BoardResponse() {
        super();
    }

    public BoardResponse(final Board board) {
        super(true, "Die Anfrage war erfolgreich.");
        this.board = board.getBoardString();
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(final String board) {
        this.board = board;
    }
}
