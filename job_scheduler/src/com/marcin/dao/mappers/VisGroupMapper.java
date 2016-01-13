package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.VisGroup;

public class VisGroupMapper implements RowMapper<VisGroup> {
	@Override
	public VisGroup mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		VisGroup group = new VisGroup();
		group.setContent(resultSet.getString(resultSet.findColumn("name")));
		group.setId(resultSet.getInt(resultSet.findColumn("resource_id")));
		group.setTitle(resultSet.getString(resultSet.findColumn("description")));
		return group;
	}
}
