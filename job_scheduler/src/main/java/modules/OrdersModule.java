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
 * Created by Marcin Frankowski on 06.09.16.
 */
public class OrdersModule {
    private OrderDAO orderDAO;
    private UserDAO userDAO;

    public OrdersModule() {}

    public OrdersModule(OrderDAO orderDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    public Order saveOrder(Order order, User user) {
        userDAO.confirmPermission(Order.class, order.getId(), user);
        order.setGroupId(user.getGroupId());
        mergeDuplicatedOrderProducts(order);
        for(OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.setGroupId(user.getGroupId());
        }
        return orderDAO.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderDAO.getUsersOrders(user);
    }

    public Integer removeOrder(Integer orderId, User user) {
        userDAO.confirmPermission(Order.class, orderId, user);
        orderDAO.remove(orderId);
        return orderId;
    }

    public List<Integer> removeOrders(List<Integer> orderIds, User user) {
        userDAO.confirmPermission(Order.class, orderIds, user);
        orderDAO.multipleRemove(orderIds);
        return orderIds;
    }

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
