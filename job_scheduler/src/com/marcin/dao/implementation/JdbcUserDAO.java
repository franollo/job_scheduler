package com.marcin.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.marcin.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.marcin.dao.DAO;
import com.marcin.dao.UserDAO;
import com.marcin.model.UserOld;

public class JdbcUserDAO extends DAO implements UserDAO {


    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT username, first_name, surname FROM users WHERE username = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, login);
            User user = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
//                        rs.getString("username"),
//                        rs.getString("first_name"),
//                        rs.getString("surname")
                );
            }
            rs.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    //GOTCHA!
                }
            }
        }
    }
}
