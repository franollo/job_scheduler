package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.Resource;

public class ResourceMapper implements RowMapper<Resource>{

	@Override
	public Resource mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
		Resource resource = new Resource();
		resource.setResourceId(resultSet.getInt(resultSet.findColumn("resource_id")));
		resource.setName(resultSet.getString(resultSet.findColumn("name")));
		resource.setDescription(resultSet.getString(resultSet.findColumn("description")));
		return resource;
	}
}
