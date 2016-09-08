package main.java.dao.model;

import main.java.model.User;
import main.java.model.common.GroupObject;

import java.util.List;

/**
 * DAO interface for User class
 * @see main.java.model.User
 * @author Marcin Frankowski
 */

public interface UserDAO {

    /**
     * Get user data from database
     * @param username user username
     * @return User object
     */
    public User getUserByLogin(String username);

    /**
     * Confirms perrmission to GroupObject with given id for given User. If user has no permission exception is thrown
     * @param objectClass class which inherits from GroupObject
     * @param idToCheck Group id to check
     * @param user user to check
     * @throws main.java.exceptions.ObjectAuthorizationException when user has no permission
     */
    public void confirmPermission(Class<? extends GroupObject> objectClass, Integer idToCheck, User user);

    /**
     * Confirms perrmission to GroupObjects with given list of ids for given User. If user has no permission exception is thrown
     * @param objectClass class which inherits from GroupObject
     * @param idsToCheck Group id to check
     * @param user user to check
     * @throws main.java.exceptions.ObjectAuthorizationException when user has no permission
     */
    public void confirmPermission(Class<? extends GroupObject> objectClass, List<Integer> idsToCheck, User user);
}
