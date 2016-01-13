package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.marcin.dao.DAO;
import com.marcin.dao.ItemDAO;
import com.marcin.dao.mappers.VisItemMapper;
import com.marcin.model.Item;
import com.marcin.model.VisItem;

public class JdbcItemDAO extends DAO implements ItemDAO{

	
	@Override
	public void createNewItem(Item item, int orderId, int itemId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("item_id", itemId);                     
		parameters.addValue("start_date", item.getStartDate());                 
		parameters.addValue("end_date", item.getEndDate());                    
		parameters.addValue("resource_id", item.getResource().getResourceId());                 
		parameters.addValue("job_id", item.getJob().getJobId());                      
		parameters.addValue("order_id", orderId);   
		parameters.addValue("color", item.getColor());
		String sql = "insert into items values(:item_id, :start_date, :end_date, :resource_id, :job_id, :order_id, :color)";
		jdbcTemplate.update(sql, parameters);
	}

	@Override
	public List<VisItem> getOrderItems(int orderId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("order_id", orderId, Types.INTEGER);
		String sql = "select  i.*, j.name from items i, jobs j  where order_id = :order_id and i.job_id = j.job_id";
		return jdbcTemplate.query(sql, parameters, new VisItemMapper());
	}
}
