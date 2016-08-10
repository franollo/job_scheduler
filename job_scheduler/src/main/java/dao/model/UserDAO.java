package main.java.dao.model;

import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.User;
import main.java.model.common.GroupObject;

import java.util.List;

public interface UserDAO {
    public User getUserByLogin(String username);
    public void confirmPermission(Class<? extends GroupObject> objectClass, Integer idToCheck, User user);
    public void confirmPermission(Class<? extends GroupObject> objectClass, List<Integer> idsToCheck, User user);
}
