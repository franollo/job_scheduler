package main.java.dao.model;

import main.java.model.OrderProduct;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface OrderProductDAO {
    public OrderProduct save(OrderProduct orderProduct);
    public void update(OrderProduct orderProduct);
    public void remove(OrderProduct orderProduct);
}
