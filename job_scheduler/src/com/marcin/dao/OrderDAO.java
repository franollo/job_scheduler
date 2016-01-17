package com.marcin.dao;

import java.util.List;

import com.marcin.model.Order;

public interface OrderDAO {
	public int createNewOrder(Order order, String username);
	public List<Order> getUserOrders(String name);
	public Order getOrder(int orderId);
	public void updateOrder(Order order, String username);
}
