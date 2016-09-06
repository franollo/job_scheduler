package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
