package main.java.modules;

import main.java.dao.model.UserDAO;
import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.User;
import main.java.model.common.GroupObject;

import java.util.List;

/**
 * Module responsible for Users and Groups processing
 * @see main.java.model.User
 * @see main.java.model.Group
 * @see main.java.dao.model.UserDAO
 * @author Marcin Frankowski
 */
public class UsersModule {
    /**
     * User Data Access Object
     */
    private UserDAO userDAO;

    /**
     * Default constructor.
     */
    public UsersModule() {
    }

    /**
     * Constructor which sets User DAO.
     * Module must be initialized with this constructor to work properlly.
     * @param userDAO User Data Access Object
     */
    public UsersModule(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Get User data
     * @param username user username
     * @return User object
     */
    public User getUser(String username) {
        return userDAO.getUserByLogin(username);
    }

    /**
     * Confirms if user has permission for GroupObject with given id to check.
     * When user has permission returns. When user has no permission throws an exception
     * @param objectClass class of object to check
     * @param idToCheck id of object to check
     * @param user user to check
     * @throws ObjectAuthorizationException when user has no permission
     */
    public void confirmPermission(Class<? extends GroupObject> objectClass, Integer idToCheck, User user) {
        try {
            userDAO.confirmPermission(objectClass, idToCheck, user);
        }
        catch (ObjectAuthorizationException e) {
            throw e;
        }
    }

    /**
     * Confirms if user has permission for GroupObjects with given list of ids to check.
     * When user has permission returns. When user has no permission throws an exception
     * @param objectClass class of object to check
     * @param idsToCheck list of object ids to check
     * @param user user to check
     * @throws ObjectAuthorizationException when user has no permission
     */
    public void confirmPermission(Class<? extends GroupObject> objectClass, List<Integer> idsToCheck, User user) {
        try {
            userDAO.confirmPermission(objectClass, idsToCheck, user);
        }
        catch (ObjectAuthorizationException e) {
            throw e;
        }
    }
}
