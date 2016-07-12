package com.marcin.dao;

import com.marcin.model.UserOld;

public interface UserDAO {
    public UserOld getUserByLogin(String login);

    public int setOrderInUse(String username, int orderId);
}
