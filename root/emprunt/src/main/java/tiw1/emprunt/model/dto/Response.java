package tiw1.emprunt.model.dto;

/**
 * La classe de reponse DTO retournee au client
 * similaire a Response de protocole HTTP mais plus simple
 */
public class Response<T> {
    // Response status
    public static final int OK = 200;
    public static final int ERROR = 401;
    public static final int NOTFOUND = 404;
    public static final int UNKNOWN_CMD = 4004;
    public static final int UNKNOWN_PARAM = 40004;

    private int status;
    private String mssg;
    private T content;

    private Response(int status, String mssg, T content) {
        this.status = status;
        this.mssg = mssg;
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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static Response create(int status, String mssg) {
        return new Response(status, mssg, null);
    }

    public static Response create(int status, String mssg, Object content) {
        return new Response(status, mssg, content);
    }

}
