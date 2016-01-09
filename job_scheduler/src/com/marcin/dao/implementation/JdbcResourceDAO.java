package com.marcin.dao.implementation;

import javax.sql.DataSource;

import com.marcin.dao.ResourceDAO;
import com.marcin.model.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbcResourceDAO implements ResourceDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
   
	
	@Override
	public void insert(Resource machine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Resource getByID(int machineID) {
		String sql = "SELECT * FROM resources WHERE resource_id = ?";
		return null;
	}


	@Override
	public List<Resource> getUserResources(String username) {
		List<Resource> resources = new LinkedList<Resource>();
		String sql = "select r.resource_id,"
				+ "			r.name,"
				+ "			r.description "
				+ "from resources r, users_resources us "
				+ "where r.resource_id = us.resource_id "
				+ "and us.user_id in (select user_id from users where username = ?)";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				resources.add(new Resource(rs.getInt("resource_id"), rs.getString("name"), rs.getString("description")));
			}
			rs.close();
			ps.close();
			return resources;
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
