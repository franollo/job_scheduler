package com.marcin.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.marcin.dao.UserDAO;
import com.marcin.model.User;

public class JdbcUserDAO implements UserDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
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
					rs.getString("username"),
					rs.getString("first_name"), 
					rs.getString("surname")
				);
			}
			rs.close();
			ps.close();
			return user;
		} 
		catch (SQLException e) {
			throw new RuntimeException(e);
		} 
		finally {
			if (conn != null) {
				try {
					conn.close();
				} 
				catch (SQLException e) {
					//GOTCHA!
				}
			}
		}
	}

}
