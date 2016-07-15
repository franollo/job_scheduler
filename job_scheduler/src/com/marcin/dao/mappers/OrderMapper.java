package com.marcin.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.marcin.model.Order;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Order order = new Order();
        order.setOrderId(resultSet.getInt(resultSet.findColumn("order_id")));
        order.setName(resultSet.getString(resultSet.findColumn("name")));
        order.setDescription(resultSet.getString(resultSet.findColumn("description")));
        //order.setStartDate(resultSet.getString(resultSet.findColumn("start_date")).substring(0, resultSet.getString(resultSet.findColumn("start_date")).length() - 2));
        String endDate = resultSet.getString(resultSet.findColumn("end_date"));
        if (endDate != null) {
            endDate = endDate.substring(0, resultSet.getString(resultSet.findColumn("end_date")).length() - 2);
        }
        //order.setEndDate(endDate);
        return order;
    }
}