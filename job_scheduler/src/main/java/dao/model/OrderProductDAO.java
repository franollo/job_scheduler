package main.java.dao.model;

import main.java.model.OrderProduct;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface OrderProductDAO {
    public void insert(OrderProduct orderProduct);
    public void update(OrderProduct orderProduct);
    public void delete(OrderProduct orderProduct);
//    public boolean checkGroupId(int orderProductId, int groupId);
}
