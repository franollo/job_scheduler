package com.marcin.dao.implementation;

import javax.sql.DataSource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.marcin.dao.ResourceDAO;
import com.marcin.model.Resource;
import com.marcin.model.Task;

import java.sql.CallableStatement;
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
	public Resource getByID(int resourceId) {
		String sql = "SELECT * FROM resources WHERE resource_id = ?";
		Connection conn = null;
		Resource resource = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, resourceId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				resource = new Resource(rs.getInt("resource_id"), rs.getString("name"), rs.getString("description"));
			}
			rs.close();
			ps.close();
			return resource;
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



	@Override
	public void createNewResource(Resource resource) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
		String procedure = "{ call create_resource(?,?,?,?) }";
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			CallableStatement ps = conn.prepareCall(procedure);
			ps.setString("resource_name", resource.getName());
			ps.setString("description", resource.getDescription());
			ps.setString("username", name);
			ps.registerOutParameter(4, java.sql.Types.INTEGER);
			ps.executeQuery();
			int resourceId = ps.getInt(4);
			ps.close();
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
