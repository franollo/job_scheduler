package main.java.exceptions;

/**
 * This is an exception that is thrown whenever the modification of GroupObject is only allowed to be done by
 * user which Group has rights to this GroupObject in database, but the User attempting the modification
 * is not a member of Group which owns this GroupObject
 * @author Marcin Frankowski
 */
public class ObjectAuthorizationException extends RuntimeException {
    public ObjectAuthorizationException() {
        super();
    }

    public ObjectAuthorizationException(String message) {
        super(message);
    }
}
