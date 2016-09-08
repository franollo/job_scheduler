package main.java.exceptions;

/**
 * This is an exception that is thrown whenever Order object is not found in database
 * @author Marcin Frankowski
 */

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
