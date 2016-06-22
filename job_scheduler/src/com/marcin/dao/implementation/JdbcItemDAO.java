package com.marcin.dao.implementation;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.marcin.dao.DAO;
import com.marcin.dao.ItemDAO;
import com.marcin.dao.mappers.VisItemMapper;
import com.marcin.model.Item;
import com.marcin.model.VisItem;

public class JdbcItemDAO extends DAO implements ItemDAO {


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
    public void createNewItem(Item item, int orderId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("create_item");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("start_date", item.getStartDate())
                .addValue("end_date", item.getEndDate())
                .addValue("resource_id", item.getResource().getResourceId())
                .addValue("job_id", item.getJob().getJobId())
                .addValue("order_id", orderId)
                .addValue("color", item.getColor());
        jdbcCall.execute(in);

    }

    @Override
    public List<VisItem> getOrderItems(int orderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("order_id", orderId, Types.INTEGER);
        String sql = "select  i.*, j.name from items i, jobs j  where order_id = :order_id and i.job_id = j.job_id";
        return jdbcTemplate.query(sql, parameters, new VisItemMapper());
    }

    @Override
    public void updateItem(VisItem item, String name) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("update_item");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("start_date", item.getStart())
                .addValue("end_date", item.getEnd())
                .addValue("name", name)
                .addValue("item_id", item.getId());
        jdbcCall.execute(in);
    }

    @Override
    public Map<Integer, Date> getEndDates(String name) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("username", name);
        String sql = "select  resource_id, max(end_date) as 'date' "
                + "from items where order_id in (select in_use from users where username = :username) "
                + "group by resource_id order by max(end_date)";
        List<Map<String, Object>> stringMap = jdbcTemplate.queryForList(sql, parameters);
        Map<Integer, Date> returnMap = new HashMap<Integer, Date>();
        for (Map m : stringMap) {
            returnMap.put((Integer) m.get("resource_id"), (Date) m.get("date"));
        }
        return returnMap;
    }

    @Override
    public Date getMaxDate(String name) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("get_max_date");
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("username", name);
        Map<String, Object> out = jdbcCall.execute(in);
        return (Date) out.get("max_date");
    }

}
