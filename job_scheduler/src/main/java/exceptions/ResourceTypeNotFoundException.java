package main.java.exceptions;

/**
 * This is an exception that is thrown whenever ResourceType object is not found in database
 * @author Marcin Frankowski
 */

public class ResourceTypeNotFoundException extends RuntimeException {
    public ResourceTypeNotFoundException() {
        super();
    }

    public ResourceTypeNotFoundException(String message) {
        super(message);
    }
}
