package main.java.com.dao.model;

import main.java.com.model.User;

public interface UserDAO {
    public User getUserByLogin(String username);
}
