package main.java.dao.model;

import main.java.model.Order;
import main.java.model.User;

import java.util.List;

public interface OrderDAO {
    public Order save(Order order);
    public void update(Order order);
    public void remove(int id);
    public void multipleRemove(List<Integer> ids);
    public List<Order> getUsersOrders(User user);
}
