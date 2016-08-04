package main.java.dao.model;

import main.java.model.Order;
import main.java.model.User;

import java.util.List;

public interface OrderDAO {
    public void insert(Order order);
    public void update(Order order);
    public void remove(Order order);
    public List<Order> getUserOrders(User user);
  //  public boolean hasPermission(User user, Order order);
//    public int createNewOrder(Order order, String username);
//
//    public List<Order> getUserOrders(String name);
//
//    public Order getOrder(int orderId);
//
//    public void updateOrder(Order order, String username);
//
//    public int getOrderInUseId(String name);
//
//    Date getStartDate(String name);
}
