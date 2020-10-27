package cm.belrose.jwtspringsecurity.utils;

/**
 * Le ResponseMessage est un message au client que nous allons utiliser dans Rest Controller et exceptions Handle
 */
public class ResponseMessage {

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
