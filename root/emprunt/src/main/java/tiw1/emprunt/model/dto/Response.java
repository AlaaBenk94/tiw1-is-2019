package tiw1.emprunt.model.dto;

import static tiw1.emprunt.model.dto.Constants.*;

/**
 * La classe de reponse DTO retournee au client
 * similaire a Response de protocole HTTP mais plus simple
 */
public class Response<T> {

    private int status;
    private String mssg;
    private T content;

    private Response(int status, String mssg, T content) {
        this.status = status;
        this.mssg = mssg;
        this.content = content;
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
