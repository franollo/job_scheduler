package main.java.dao.jpa;

import main.java.dao.model.OrderDAO;
import main.java.model.Order;
import main.java.model.OrderProduct;
import main.java.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */

@Repository("orderDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class JPAOrderDAO extends JPADAO implements OrderDAO {
    @Override
    public Order save(Order order) {
        if(order.getId() == null) {
            List<OrderProduct> orderProducts = order.getOrderProducts();
            order.setOrderProducts(new LinkedList<>());
            Order mergeOrder = entityManager.merge(order);
            for(OrderProduct orderProduct : orderProducts) {
                mergeOrder.addOrderProduct(orderProduct);
            }
            return mergeOrder;
        }
        else {
            Order existingOrder = entityManager.find(Order.class, order.getId());
            existingOrder.setName(order.getName());
            existingOrder.setDescription(order.getDescription());
            existingOrder.setDueDate(order.getDueDate());
            for(OrderProduct orderProduct : order.getOrderProducts()) {
                int index = existingOrder.getOrderProducts().indexOf(orderProduct);
                if(index > -1) {
                    existingOrder.getOrderProducts().get(index).setAmount(orderProduct.getAmount());
                }
                else {
                    existingOrder.addOrderProduct(orderProduct);
                }
            }
            Iterator<OrderProduct> iterator = existingOrder.getOrderProducts().iterator();
            while(iterator.hasNext()) {
                OrderProduct orderProduct = iterator.next();
                int index = order.getOrderProducts().indexOf(orderProduct);
                if(index == -1) {
                    iterator.remove();
                }
            }
            return existingOrder;
        }
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public void remove(int id) {
        entityManager
                .createQuery("delete from Order o where o.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void multipleRemove(List<Integer> ids) {
        entityManager
                .createQuery("delete from Order o where o.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public List<Order> getUsersOrders(User user) {
        String queryString = "SELECT o from Order o inner join User u " +
                "on o.groupId = u.groupId " +
                "where u.username = :username";
        TypedQuery<Order> query = entityManager.createQuery(queryString, Order.class);
        try {
            return query.setParameter("username", user.getUsername()).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
