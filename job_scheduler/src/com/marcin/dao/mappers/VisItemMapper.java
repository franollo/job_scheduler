package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.VisItem;

public class VisItemMapper implements RowMapper<VisItem>{

	@Override
	public VisItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		VisItem item = new VisItem();
		item.setId(resultSet.getInt(resultSet.findColumn("item_id")));
		item.setStart(resultSet.getString(resultSet.findColumn("start_date")).substring(0,resultSet.getString(resultSet.findColumn("start_date")).length()-2));
		item.setEnd(resultSet.getString(resultSet.findColumn("end_date")).substring(0,resultSet.getString(resultSet.findColumn("end_date")).length()-2));
		item.setGroup(resultSet.getInt(resultSet.findColumn("resource_id")));
		item.setClassName(resultSet.getString(resultSet.findColumn("color")));
		item.setTitle(resultSet.getString(resultSet.findColumn("name")));
		item.setContent(" ");
		item.setType("range");
		return item;
	}

}
