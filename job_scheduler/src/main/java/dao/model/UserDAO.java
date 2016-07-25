package main.java.dao.model;

import main.java.exceptions.ObjectAuthorizationException;
import main.java.model.User;
import main.java.model.common.GroupObject;

public interface UserDAO {
    public User getUserByLogin(String username);
    public void hasPermission(GroupObject groupObject, User user) throws ObjectAuthorizationException;
}
