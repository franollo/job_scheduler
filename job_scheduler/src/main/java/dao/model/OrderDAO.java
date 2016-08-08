package main.java.dao.model;

import main.java.model.Order;
import main.java.model.User;

import java.util.List;

public interface OrderDAO {
    public void insert(Order order);
    public void update(Order order);
    public void remove(Order order);
    public List<Order> getUsersOrders(User user);
}
