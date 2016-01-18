package com.marcin.dao;

import java.util.Date;
import java.util.List;

import com.marcin.model.Order;

public interface OrderDAO {
	public int createNewOrder(Order order, String username);
	public List<Order> getUserOrders(String name);
	public Order getOrder(int orderId);
	public void updateOrder(Order order, String username);
	public int getOrderInUseId(String name);
	Date getStartDate(String name);
}
