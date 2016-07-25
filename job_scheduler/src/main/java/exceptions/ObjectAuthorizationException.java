package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class ObjectAuthorizationException extends RuntimeException {
    public ObjectAuthorizationException() {
        super();
    }

    public ObjectAuthorizationException(String message) {
        super(message);
    }
}
