package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super();
    }

    public ItemNotFoundException(String message) {
        super(message);
    }
}
