package main.java.dao.model;

import main.java.model.Order;
import main.java.model.User;

import java.util.List;

/**
 * DAO interface for Order class
 * @see main.java.model.Order
 * @author Marcin Frankowski
 */

public interface OrderDAO {
    /**
     * Update order in database or insert if not exists
     * @param order entity to save
     * @return entity saved in database
     */
    public Order save(Order order);

    /**
     * Remove order with given id from database
     * @param  id given resource id
     */
    public void remove(int id);

    /**
     * Remove orders with given list of ids from database
     * @param ids list of ids of orders to remove
     */
    public void multipleRemove(List<Integer> ids);

    /**
     * Get orders to which user has permission from database
     * @param user given user
     * @return list of user's orders
     */
    public List<Order> getUsersOrders(User user);
}
