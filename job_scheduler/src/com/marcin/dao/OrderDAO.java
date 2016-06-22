package com.marcin.dao;

import com.marcin.model.Order;

import java.util.Date;
import java.util.List;

public interface OrderDAO {
    public int createNewOrder(Order order, String username);

    public List<Order> getUserOrders(String name);

    public Order getOrder(int orderId);

    public void updateOrder(Order order, String username);

    public int getOrderInUseId(String name);

    Date getStartDate(String name);
}
