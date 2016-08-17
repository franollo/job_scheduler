package main.java.controllers;

import main.java.exceptions.ObjectAuthorizationException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Marcin Frankowski on 25.07.2016.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({GenericJDBCException.class, ConstraintViolationException.class})
    public void handleGenericJDBCException(HttpServletResponse response, Exception e) {
        response.setStatus(400);
    }

    @ExceptionHandler(ObjectAuthorizationException.class)
    public void handleObjectAuthorizationException(HttpServletResponse response, Exception e) {
        response.setStatus(401);
    }
}
