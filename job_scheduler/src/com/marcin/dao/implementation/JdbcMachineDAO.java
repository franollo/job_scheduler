package com.marcin.dao.implementation;

import javax.sql.DataSource;

import com.marcin.dao.MachineDAO;
import com.marcin.model.Machine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcMachineDAO implements MachineDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
   
	
	@Override
	public void insert(Machine machine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Machine getByID(int machineID) {
		String sql = "SELECT * FROM operating_positions WHERE OP_ID = ?";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, machineID);
			Machine machine = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				machine = new Machine(
					rs.getInt("OP_ID"),
					rs.getString("NAME"), 
					rs.getInt("GROUP_ID")
				);
			}
			rs.close();
			ps.close();
			return machine;
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
