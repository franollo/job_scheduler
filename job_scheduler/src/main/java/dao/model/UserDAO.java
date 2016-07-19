package main.java.dao.model;

import main.java.model.User;

public interface UserDAO {
    public User getUserByLogin(String username);
}
