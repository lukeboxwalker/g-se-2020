package de.techfak.se.lwalkenhorst.domain.server.rest.response;

public class ParticipateResponse implements ResponseBody {
    private boolean success;
    private String uuid;
    private String board;

    public ParticipateResponse() {
    }

    public ParticipateResponse(boolean success, String uuid, String board) {
        this.success = success;
        this.uuid = uuid;
        this.board = board;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
