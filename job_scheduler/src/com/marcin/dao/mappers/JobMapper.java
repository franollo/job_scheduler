package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.Job;


public class JobMapper implements RowMapper<Job>{
	@Override
	public Job mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Job job = new Job();
		job.setJobId(resultSet.getInt(resultSet.findColumn("job_id")));
		job.setName(resultSet.getString(resultSet.findColumn("name")));
		job.setDescription(resultSet.getString(resultSet.findColumn("description")));
		job.setOrder(resultSet.getBoolean(resultSet.findColumn("in_order")));
		return job;
	}
}
