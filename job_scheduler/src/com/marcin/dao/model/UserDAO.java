package com.marcin.dao.model;

import com.marcin.model.User;

public interface UserDAO {
    public User getUserByLogin(String username);
}
