package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.marcin.dao.DAO;
import com.marcin.dao.OrderDAO;
import com.marcin.dao.mappers.OrderMapper;
import com.marcin.model.Order;

public class JdbcOrderDAO extends DAO implements OrderDAO{

	
	@Override
	public int createNewOrder(Order order, String username) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_order");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("order_name", order.getName())
				.addValue("description", order.getDescription())
				.addValue("start_date", order.getStartDate())
				.addValue("username", username);
		Map<String,Object> out = jdbcCall.execute(in);
		return (int) out.get("order_id");
	}

	@Override
	public List<Order> getUserOrders(String username) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username, Types.VARCHAR);
		String sql = "select o.* "
				+ "from orders o, users_orders uo "
				+ "where o.order_id = uo.order_id "
				+ "and uo.user_id in "
				+ "(select user_id from users where username = :username)";
		return jdbcTemplate.query(sql, parameters, new OrderMapper());
	}

	@Override
	public Order getOrder(int orderId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("orderId", orderId, Types.INTEGER);
		String sql = "select * "
				+ "from orders o "
				+ "where o.order_id = :orderId ";
		return jdbcTemplate.query(sql, parameters, new OrderMapper()).get(0);
	}

	@Override
	public void updateOrder(Order order, String username) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_order");
		SqlParameterSource in = new MapSqlParameterSource()
				.addValue("order_name", order.getName())
				.addValue("description", order.getDescription())
				.addValue("start_date", order.getStartDate())
				.addValue("username", username);
		jdbcCall.execute(in);
		
	}

}
