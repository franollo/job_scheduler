package main.java.dao.model;

import main.java.model.User;
import main.java.model.common.GroupObject;

public interface UserDAO {
    public User getUserByLogin(String username);
    public boolean hasPermission(GroupObject groupObject, User user);
}
