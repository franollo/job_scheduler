package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.marcin.dao.DAO;
import com.marcin.dao.ResourceDAO;
import com.marcin.dao.mappers.ResourceMapper;
import com.marcin.dao.mappers.VisGroupMapper;
import com.marcin.model.Resource;
import com.marcin.model.VisGroup;


public class JdbcResourceDAO extends DAO implements ResourceDAO
{

	@Override
	public Resource getByID(int resourceId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("resource_id", resourceId, Types.INTEGER);
		String sql = "SELECT * FROM resources WHERE resource_id = :resource_id";
		return jdbcTemplate.query(sql, parameters, new ResourceMapper()).get(0);
	}


	@Override
	public List<Resource> getUserResources(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username, Types.VARCHAR);
		String sql = "select r.resource_id,"
				+ "r.name, r.description "
				+ "from resources r, users_resources us "
				+ "where r.resource_id = us.resource_id "
				+ "and us.user_id in (select user_id from users where username = :username)";
		return jdbcTemplate.query(sql, parameters, new ResourceMapper());
	}



	@Override
	public void createNewResource(Resource resource, String name) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_resource");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("resource_name", resource.getName())
				.addValue("description", resource.getDescription())
				.addValue("username", name);
		jdbcCall.execute(in);		
	}

	@Override
	public List<VisGroup> getOrderGroups(int orderId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("order_id", orderId, Types.INTEGER);
		String sql = "select * from resources where resource_id in "
				+ "(select distinct resource_id from items "
				+ "where order_id = :order_id)";
		return jdbcTemplate.query(sql, parameters, new VisGroupMapper());
	}

	@Override
	public void deleteResource(Resource resource, String name) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("delete_resource");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("resource_id", resource.getResourceId())
				.addValue("username", name);
		jdbcCall.execute(in);
	}

	@Override
	public void updateResource(Resource resource, String name) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("update_resource");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("resource_id", resource.getResourceId())
				.addValue("name", resource.getName())
				.addValue("description", resource.getDescription())
				.addValue("username", name);
		jdbcCall.execute(in);
		
	}

}
