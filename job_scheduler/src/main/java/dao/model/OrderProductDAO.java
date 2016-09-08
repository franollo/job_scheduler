package main.java.dao.model;

import main.java.model.OrderProduct;

/**
 * DAO interface for OrderProduct class
 * @see main.java.model.OrderProduct
 * @author Marcin Frankowski
 */

public interface OrderProductDAO {
    public OrderProduct save(OrderProduct orderProduct);
    public void update(OrderProduct orderProduct);
    public void remove(OrderProduct orderProduct);
}
