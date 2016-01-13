package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.Task;

public class TaskMapper implements RowMapper<Task>{

	@Override
	public Task mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Task task = new Task();
		task.setResourceId(resultSet.getInt(resultSet.findColumn("resource_id")));
		task.setSecondsDuration(resultSet.getInt(resultSet.findColumn("duration")));
		return task;
	}

}
