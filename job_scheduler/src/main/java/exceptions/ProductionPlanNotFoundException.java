package main.java.exceptions;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */
public class ProductionPlanNotFoundException extends RuntimeException {
    public ProductionPlanNotFoundException() {
        super();
    }

    public ProductionPlanNotFoundException(String message) {
        super(message);
    }
}
