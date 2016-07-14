package com.marcin.dao;

import com.marcin.model.User;

public interface UserDAO {
    public User getUserByLogin(String username);
    public boolean isUsersObject(Object object, String username);
}
