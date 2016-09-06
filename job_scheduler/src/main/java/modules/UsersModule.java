package main.java.modules;

import main.java.dao.model.UserDAO;
import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.User;
import main.java.model.common.GroupObject;

import java.util.List;

/**
 * Created by Marcin Frankowski on 06.09.16.
 */
public class UsersModule {
    private UserDAO userDAO;

    public UsersModule() {
    }

    public UsersModule(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(String username) {
        return userDAO.getUserByLogin(username);
    }

    public void confirmPermission(Class<? extends GroupObject> objectClass, Integer idToCheck, User user) {
        try {
            userDAO.confirmPermission(objectClass, idToCheck, user);
        }
        catch (ObjectAuthorizationException e) {
            throw e;
        }
    }

    public void confirmPermission(Class<? extends GroupObject> objectClass, List<Integer> idsToCheck, User user) {
        try {
            userDAO.confirmPermission(objectClass, idsToCheck, user);
        }
        catch (ObjectAuthorizationException e) {
            throw e;
        }
    }
}
