package tiw1.emprunt.model.dto;

/**
 * La classe de reponse DTO retournee au client
 * similaire a Response de protocole HTTP mais plus simple
 */
public class Response {
    // Response status
    public static final int OK = 200;
    public static final int ERROR = 401;
    public static final int NOTFOUND = 404;
    public static final int UNKNOWN_CMD = 4004;
    public static final int UNKNOWN_PARAM = 40004;

    private int status;
    private Object content;

    public Response(int status, Object content) {
        this.status = status;
        this.content = content;
    }

    public boolean isOK() {
        return (status == OK);
    }

    public boolean isERROR() {
        return (status == ERROR);
    }

    public boolean isNOTFOUND() {
        return (status == NOTFOUND);
    }

    public boolean isUNKONWN_CMD() {
        return (status == UNKNOWN_CMD);
    }

    public boolean isUNKONWN_PARAM() {
        return (status == UNKNOWN_PARAM);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static Response create(int status, Object content) {
        return new Response(status, content);
    }

}
