package com.marcin.dao.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.marcin.dao.OrderDAO;
import com.marcin.model.Resource;

public class JdbcOrderDAO implements OrderDAO{

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public int createNewOrder(String orderName, Date startDate) {
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = formater.format(startDate);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    System.out.println("To jest użytkownik: " + name);
		String procedure = "{ call create_order(?,?,?,?) }";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			CallableStatement ps = conn.prepareCall(procedure);
			ps.setString("order_name", orderName);
			ps.setString("start_date", sqlDate);
			ps.setString("username", name);
			ps.registerOutParameter(4, java.sql.Types.INTEGER);
			ps.executeQuery();
			int orderId = ps.getInt(4);
			System.out.println(orderId);
			ps.close();
			return orderId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}

}
