package main.java.exceptions;

/**
 * This is an exception that is thrown whenever Product object is not found in database
 * @author Marcin Frankowski
 */

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
