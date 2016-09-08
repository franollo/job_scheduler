package main.java.exceptions;

/**
 * This is an exception that is thrown whenever ProductionPlan object is not found in database
 * @author Marcin Frankowski
 */

public class ProductionPlanNotFoundException extends RuntimeException {
    public ProductionPlanNotFoundException() {
        super();
    }

    public ProductionPlanNotFoundException(String message) {
        super(message);
    }
}
