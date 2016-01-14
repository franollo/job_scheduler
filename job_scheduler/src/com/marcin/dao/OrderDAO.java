package com.marcin.dao;

import java.util.Date;
import java.util.List;

import com.marcin.model.Order;

public interface OrderDAO {
	public int createNewOrder(String orderName, Date startDate);
	public List<Order> getUserOrders(String name);
}
