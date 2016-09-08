package main.java.controllers;

import main.java.exceptions.ObjectAuthorizationException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletResponse;

/**
 * Global exception handler for all controllers
 * @author Marcin Frankowski
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Sets HTTP status 400 when exception is caught
     * @param response HTTP response
     * @param e exception to handle
     */
    @ExceptionHandler({GenericJDBCException.class, ConstraintViolationException.class, PersistenceException.class})
    public void handleGenericJDBCException(HttpServletResponse response, Exception e) {
        response.setStatus(400);
    }

    /**
     * Sets HTTP status 401 when exception is caught
     * @param response HTTP response
     * @param e exception to handle
     */
    @ExceptionHandler(ObjectAuthorizationException.class)
    public void handleObjectAuthorizationException(HttpServletResponse response, Exception e) {
        response.setStatus(401);
    }
}
