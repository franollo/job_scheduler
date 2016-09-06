package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
