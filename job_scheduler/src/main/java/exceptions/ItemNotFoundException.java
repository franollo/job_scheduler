package main.java.exceptions;

/**
 * This is an exception that is thrown whenever Item object is not found in database
 * @author Marcin Frankowski
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
