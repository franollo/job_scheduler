package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class ResourceTypeNotFoundException extends RuntimeException {
    public ResourceTypeNotFoundException() {
        super();
    }

    public ResourceTypeNotFoundException(String message) {
        super(message);
    }
}
