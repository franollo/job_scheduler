package main.java.modules;

import main.java.dao.model.OrderDAO;
import main.java.dao.model.UserDAO;
import main.java.model.Order;
import main.java.model.OrderProduct;
import main.java.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Module responsible for Orders processing
 * @see main.java.model.Order
 * @author Marcin Frankowski
 */
public class OrdersModule {
    /**
     * Order Data Access Object
     */
    private OrderDAO orderDAO;

    /**
     * User Data Access Object
     */
    private UserDAO userDAO;

    /**
     * Default constructor
     */
    public OrdersModule() {}

    /**
     * Constructor which sets Order DAOand User DAO.
     * Module must be initialized with this constructor to work properlly.
     * @param orderDAO Order Data Access Object
     * @param userDAO User Data Access Object
     */
    public OrdersModule(OrderDAO orderDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    /**
     * Save new or existing Order in system.
     * @param order order to save
     * @param user user who wants to save order
     * @return saved order
     */
    public Order saveOrder(Order order, User user) {
        userDAO.confirmPermission(Order.class, order.getId(), user);
        order.setGroupId(user.getGroupId());
        mergeDuplicatedOrderProducts(order);
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.setGroupId(user.getGroupId());
        }
        return orderDAO.save(order);
    }

    /**
     * Get all orders to which user has permission.
     * @param user user who wants to get orders
     * @return List of user's orders
     */
    public List<Order> getUserOrders(User user) {
        return orderDAO.getUsersOrders(user);
    }

    /**
     * Remove order with given id from system.
     * @param orderId id of order to remove
     * @param user user who wants to remove order
     * @return removed order id
     */
    public Integer removeOrder(Integer orderId, User user) {
        userDAO.confirmPermission(Order.class, orderId, user);
        orderDAO.remove(orderId);
        return orderId;
    }

    /**
     * Remove order with given list of ids from system.
     * @param orderIds list of ids of orders to remove
     * @param user user who wants to remove orders
     * @return list of removed orders's ids
     */
    public List<Integer> removeOrders(List<Integer> orderIds, User user) {
        userDAO.confirmPermission(Order.class, orderIds, user);
        orderDAO.multipleRemove(orderIds);
        return orderIds;
    }

    /**
     * Helper function. It merges duplicated order products in order
     * @param order order which order products are merged
     */
    private void mergeDuplicatedOrderProducts(Order order) {
        int index;
        List<OrderProduct> tempList = new ArrayList<>();
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            index = tempList.indexOf(orderProduct);
            if(index > -1) {
                tempList.get(index).addAmount(orderProduct.getAmount());
            }
            else {
                tempList.add(orderProduct);
            }
        }
        order.setOrderProducts(new LinkedList<>(tempList));
    }
}
